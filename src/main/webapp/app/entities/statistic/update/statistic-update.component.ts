import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITask } from 'app/entities/task/task.model';
import { TaskService } from 'app/entities/task/service/task.service';
import { IPlayer } from 'app/entities/player/player.model';
import { PlayerService } from 'app/entities/player/service/player.service';
import { StatisticService } from '../service/statistic.service';
import { IStatistic } from '../statistic.model';
import { StatisticFormService, StatisticFormGroup } from './statistic-form.service';

@Component({
  standalone: true,
  selector: 'jhi-statistic-update',
  templateUrl: './statistic-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class StatisticUpdateComponent implements OnInit {
  isSaving = false;
  statistic: IStatistic | null = null;

  tasksSharedCollection: ITask[] = [];
  playersSharedCollection: IPlayer[] = [];

  protected statisticService = inject(StatisticService);
  protected statisticFormService = inject(StatisticFormService);
  protected taskService = inject(TaskService);
  protected playerService = inject(PlayerService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: StatisticFormGroup = this.statisticFormService.createStatisticFormGroup();

  compareTask = (o1: ITask | null, o2: ITask | null): boolean => this.taskService.compareTask(o1, o2);

  comparePlayer = (o1: IPlayer | null, o2: IPlayer | null): boolean => this.playerService.comparePlayer(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statistic }) => {
      this.statistic = statistic;
      if (statistic) {
        this.updateForm(statistic);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const statistic = this.statisticFormService.getStatistic(this.editForm);
    if (statistic.id !== null) {
      this.subscribeToSaveResponse(this.statisticService.update(statistic));
    } else {
      this.subscribeToSaveResponse(this.statisticService.create(statistic));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStatistic>>): void {
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

  protected updateForm(statistic: IStatistic): void {
    this.statistic = statistic;
    this.statisticFormService.resetForm(this.editForm, statistic);

    this.tasksSharedCollection = this.taskService.addTaskToCollectionIfMissing<ITask>(
      this.tasksSharedCollection,
      ...(statistic.tasks ?? []),
    );
    this.playersSharedCollection = this.playerService.addPlayerToCollectionIfMissing<IPlayer>(
      this.playersSharedCollection,
      statistic.employee,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.taskService
      .query()
      .pipe(map((res: HttpResponse<ITask[]>) => res.body ?? []))
      .pipe(map((tasks: ITask[]) => this.taskService.addTaskToCollectionIfMissing<ITask>(tasks, ...(this.statistic?.tasks ?? []))))
      .subscribe((tasks: ITask[]) => (this.tasksSharedCollection = tasks));

    this.playerService
      .query()
      .pipe(map((res: HttpResponse<IPlayer[]>) => res.body ?? []))
      .pipe(map((players: IPlayer[]) => this.playerService.addPlayerToCollectionIfMissing<IPlayer>(players, this.statistic?.employee)))
      .subscribe((players: IPlayer[]) => (this.playersSharedCollection = players));
  }
}
