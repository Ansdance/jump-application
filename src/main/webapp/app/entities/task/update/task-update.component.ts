import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IStatistic } from 'app/entities/statistic/statistic.model';
import { StatisticService } from 'app/entities/statistic/service/statistic.service';
import { ITask } from '../task.model';
import { TaskService } from '../service/task.service';
import { TaskFormService, TaskFormGroup } from './task-form.service';

@Component({
  standalone: true,
  selector: 'jhi-task-update',
  templateUrl: './task-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TaskUpdateComponent implements OnInit {
  isSaving = false;
  task: ITask | null = null;

  statisticsSharedCollection: IStatistic[] = [];

  protected taskService = inject(TaskService);
  protected taskFormService = inject(TaskFormService);
  protected statisticService = inject(StatisticService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TaskFormGroup = this.taskFormService.createTaskFormGroup();

  compareStatistic = (o1: IStatistic | null, o2: IStatistic | null): boolean => this.statisticService.compareStatistic(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ task }) => {
      this.task = task;
      if (task) {
        this.updateForm(task);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const task = this.taskFormService.getTask(this.editForm);
    if (task.id !== null) {
      this.subscribeToSaveResponse(this.taskService.update(task));
    } else {
      this.subscribeToSaveResponse(this.taskService.create(task));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITask>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(task: ITask): void {
    this.task = task;
    this.taskFormService.resetForm(this.editForm, task);

    this.statisticsSharedCollection = this.statisticService.addStatisticToCollectionIfMissing<IStatistic>(
      this.statisticsSharedCollection,
      ...(task.statistics ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.statisticService
      .query()
      .pipe(map((res: HttpResponse<IStatistic[]>) => res.body ?? []))
      .pipe(
        map((statistics: IStatistic[]) =>
          this.statisticService.addStatisticToCollectionIfMissing<IStatistic>(statistics, ...(this.task?.statistics ?? [])),
        ),
      )
      .subscribe((statistics: IStatistic[]) => (this.statisticsSharedCollection = statistics));
  }
}
