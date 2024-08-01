import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ILocation } from 'app/entities/location/location.model';
import { LocationService } from 'app/entities/location/service/location.service';
import { ITournament } from '../tournament.model';
import { TournamentService } from '../service/tournament.service';
import { TournamentFormService, TournamentFormGroup } from './tournament-form.service';

@Component({
  standalone: true,
  selector: 'jhi-tournament-update',
  templateUrl: './tournament-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TournamentUpdateComponent implements OnInit {
  isSaving = false;
  tournament: ITournament | null = null;

  locationsCollection: ILocation[] = [];

  protected tournamentService = inject(TournamentService);
  protected tournamentFormService = inject(TournamentFormService);
  protected locationService = inject(LocationService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TournamentFormGroup = this.tournamentFormService.createTournamentFormGroup();

  compareLocation = (o1: ILocation | null, o2: ILocation | null): boolean => this.locationService.compareLocation(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tournament }) => {
      this.tournament = tournament;
      if (tournament) {
        this.updateForm(tournament);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tournament = this.tournamentFormService.getTournament(this.editForm);
    if (tournament.id !== null) {
      this.subscribeToSaveResponse(this.tournamentService.update(tournament));
    } else {
      this.subscribeToSaveResponse(this.tournamentService.create(tournament));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITournament>>): void {
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

  protected updateForm(tournament: ITournament): void {
    this.tournament = tournament;
    this.tournamentFormService.resetForm(this.editForm, tournament);

    this.locationsCollection = this.locationService.addLocationToCollectionIfMissing<ILocation>(
      this.locationsCollection,
      tournament.location,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.locationService
      .query({ filter: 'tournament-is-null' })
      .pipe(map((res: HttpResponse<ILocation[]>) => res.body ?? []))
      .pipe(
        map((locations: ILocation[]) =>
          this.locationService.addLocationToCollectionIfMissing<ILocation>(locations, this.tournament?.location),
        ),
      )
      .subscribe((locations: ILocation[]) => (this.locationsCollection = locations));
  }
}
