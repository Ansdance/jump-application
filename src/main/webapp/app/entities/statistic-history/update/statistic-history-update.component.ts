import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IStatistic } from 'app/entities/statistic/statistic.model';
import { StatisticService } from 'app/entities/statistic/service/statistic.service';
import { ITournament } from 'app/entities/tournament/tournament.model';
import { TournamentService } from 'app/entities/tournament/service/tournament.service';
import { IPlayer } from 'app/entities/player/player.model';
import { PlayerService } from 'app/entities/player/service/player.service';
import { Language } from 'app/entities/enumerations/language.model';
import { StatisticHistoryService } from '../service/statistic-history.service';
import { IStatisticHistory } from '../statistic-history.model';
import { StatisticHistoryFormService, StatisticHistoryFormGroup } from './statistic-history-form.service';

@Component({
  standalone: true,
  selector: 'jhi-statistic-history-update',
  templateUrl: './statistic-history-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class StatisticHistoryUpdateComponent implements OnInit {
  isSaving = false;
  statisticHistory: IStatisticHistory | null = null;
  languageValues = Object.keys(Language);

  statisticsCollection: IStatistic[] = [];
  departmentsCollection: ITournament[] = [];
  employeesCollection: IPlayer[] = [];

  protected statisticHistoryService = inject(StatisticHistoryService);
  protected statisticHistoryFormService = inject(StatisticHistoryFormService);
  protected statisticService = inject(StatisticService);
  protected tournamentService = inject(TournamentService);
  protected playerService = inject(PlayerService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: StatisticHistoryFormGroup = this.statisticHistoryFormService.createStatisticHistoryFormGroup();

  compareStatistic = (o1: IStatistic | null, o2: IStatistic | null): boolean => this.statisticService.compareStatistic(o1, o2);

  compareTournament = (o1: ITournament | null, o2: ITournament | null): boolean => this.tournamentService.compareTournament(o1, o2);

  comparePlayer = (o1: IPlayer | null, o2: IPlayer | null): boolean => this.playerService.comparePlayer(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statisticHistory }) => {
      this.statisticHistory = statisticHistory;
      if (statisticHistory) {
        this.updateForm(statisticHistory);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const statisticHistory = this.statisticHistoryFormService.getStatisticHistory(this.editForm);
    if (statisticHistory.id !== null) {
      this.subscribeToSaveResponse(this.statisticHistoryService.update(statisticHistory));
    } else {
      this.subscribeToSaveResponse(this.statisticHistoryService.create(statisticHistory));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStatisticHistory>>): void {
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

  protected updateForm(statisticHistory: IStatisticHistory): void {
    this.statisticHistory = statisticHistory;
    this.statisticHistoryFormService.resetForm(this.editForm, statisticHistory);

    this.statisticsCollection = this.statisticService.addStatisticToCollectionIfMissing<IStatistic>(
      this.statisticsCollection,
      statisticHistory.statistic,
    );
    this.departmentsCollection = this.tournamentService.addTournamentToCollectionIfMissing<ITournament>(
      this.departmentsCollection,
      statisticHistory.department,
    );
    this.employeesCollection = this.playerService.addPlayerToCollectionIfMissing<IPlayer>(
      this.employeesCollection,
      statisticHistory.employee,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.statisticService
      .query({ filter: 'statistichistory-is-null' })
      .pipe(map((res: HttpResponse<IStatistic[]>) => res.body ?? []))
      .pipe(
        map((statistics: IStatistic[]) =>
          this.statisticService.addStatisticToCollectionIfMissing<IStatistic>(statistics, this.statisticHistory?.statistic),
        ),
      )
      .subscribe((statistics: IStatistic[]) => (this.statisticsCollection = statistics));

    this.tournamentService
      .query({ filter: 'statistichistory-is-null' })
      .pipe(map((res: HttpResponse<ITournament[]>) => res.body ?? []))
      .pipe(
        map((tournaments: ITournament[]) =>
          this.tournamentService.addTournamentToCollectionIfMissing<ITournament>(tournaments, this.statisticHistory?.department),
        ),
      )
      .subscribe((tournaments: ITournament[]) => (this.departmentsCollection = tournaments));

    this.playerService
      .query({ filter: 'statistichistory-is-null' })
      .pipe(map((res: HttpResponse<IPlayer[]>) => res.body ?? []))
      .pipe(
        map((players: IPlayer[]) => this.playerService.addPlayerToCollectionIfMissing<IPlayer>(players, this.statisticHistory?.employee)),
      )
      .subscribe((players: IPlayer[]) => (this.employeesCollection = players));
  }
}
