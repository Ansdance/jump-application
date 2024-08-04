import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITournament, NewTournament } from '../tournament.model';

export type PartialUpdateTournament = Partial<ITournament> & Pick<ITournament, 'id'>;

export type EntityResponseType = HttpResponse<ITournament>;
export type EntityArrayResponseType = HttpResponse<ITournament[]>;

@Injectable({ providedIn: 'root' })
export class TournamentService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tournaments');

  create(tournament: NewTournament): Observable<EntityResponseType> {
    return this.http.post<ITournament>(this.resourceUrl, tournament, { observe: 'response' });
  }

  update(tournament: ITournament): Observable<EntityResponseType> {
    return this.http.put<ITournament>(`${this.resourceUrl}/${this.getTournamentIdentifier(tournament)}`, tournament, {
      observe: 'response',
    });
  }

  partialUpdate(tournament: PartialUpdateTournament): Observable<EntityResponseType> {
    return this.http.patch<ITournament>(`${this.resourceUrl}/${this.getTournamentIdentifier(tournament)}`, tournament, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITournament>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITournament[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTournamentIdentifier(tournament: Pick<ITournament, 'id'>): number {
    return tournament.id;
  }

  compareTournament(o1: Pick<ITournament, 'id'> | null, o2: Pick<ITournament, 'id'> | null): boolean {
    return o1 && o2 ? this.getTournamentIdentifier(o1) === this.getTournamentIdentifier(o2) : o1 === o2;
  }

  addTournamentToCollectionIfMissing<Type extends Pick<ITournament, 'id'>>(
    tournamentCollection: Type[],
    ...tournamentsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tournaments: Type[] = tournamentsToCheck.filter(isPresent);
    if (tournaments.length > 0) {
      const tournamentCollectionIdentifiers = tournamentCollection.map(tournamentItem => this.getTournamentIdentifier(tournamentItem));
      const tournamentsToAdd = tournaments.filter(tournamentItem => {
        const tournamentIdentifier = this.getTournamentIdentifier(tournamentItem);
        if (tournamentCollectionIdentifiers.includes(tournamentIdentifier)) {
          return false;
        }
        tournamentCollectionIdentifiers.push(tournamentIdentifier);
        return true;
      });
      return [...tournamentsToAdd, ...tournamentCollection];
    }
    return tournamentCollection;
  }
}
