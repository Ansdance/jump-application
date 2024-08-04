import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStatisticHistory, NewStatisticHistory } from '../statistic-history.model';

export type PartialUpdateStatisticHistory = Partial<IStatisticHistory> & Pick<IStatisticHistory, 'id'>;

type RestOf<T extends IStatisticHistory | NewStatisticHistory> = Omit<T, 'startDate' | 'endDate'> & {
  startDate?: string | null;
  endDate?: string | null;
};

export type RestStatisticHistory = RestOf<IStatisticHistory>;

export type NewRestStatisticHistory = RestOf<NewStatisticHistory>;

export type PartialUpdateRestStatisticHistory = RestOf<PartialUpdateStatisticHistory>;

export type EntityResponseType = HttpResponse<IStatisticHistory>;
export type EntityArrayResponseType = HttpResponse<IStatisticHistory[]>;

@Injectable({ providedIn: 'root' })
export class StatisticHistoryService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/statistic-histories');

  create(statisticHistory: NewStatisticHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(statisticHistory);
    return this.http
      .post<RestStatisticHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(statisticHistory: IStatisticHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(statisticHistory);
    return this.http
      .put<RestStatisticHistory>(`${this.resourceUrl}/${this.getStatisticHistoryIdentifier(statisticHistory)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(statisticHistory: PartialUpdateStatisticHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(statisticHistory);
    return this.http
      .patch<RestStatisticHistory>(`${this.resourceUrl}/${this.getStatisticHistoryIdentifier(statisticHistory)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestStatisticHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestStatisticHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getStatisticHistoryIdentifier(statisticHistory: Pick<IStatisticHistory, 'id'>): number {
    return statisticHistory.id;
  }

  compareStatisticHistory(o1: Pick<IStatisticHistory, 'id'> | null, o2: Pick<IStatisticHistory, 'id'> | null): boolean {
    return o1 && o2 ? this.getStatisticHistoryIdentifier(o1) === this.getStatisticHistoryIdentifier(o2) : o1 === o2;
  }

  addStatisticHistoryToCollectionIfMissing<Type extends Pick<IStatisticHistory, 'id'>>(
    statisticHistoryCollection: Type[],
    ...statisticHistoriesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const statisticHistories: Type[] = statisticHistoriesToCheck.filter(isPresent);
    if (statisticHistories.length > 0) {
      const statisticHistoryCollectionIdentifiers = statisticHistoryCollection.map(statisticHistoryItem =>
        this.getStatisticHistoryIdentifier(statisticHistoryItem),
      );
      const statisticHistoriesToAdd = statisticHistories.filter(statisticHistoryItem => {
        const statisticHistoryIdentifier = this.getStatisticHistoryIdentifier(statisticHistoryItem);
        if (statisticHistoryCollectionIdentifiers.includes(statisticHistoryIdentifier)) {
          return false;
        }
        statisticHistoryCollectionIdentifiers.push(statisticHistoryIdentifier);
        return true;
      });
      return [...statisticHistoriesToAdd, ...statisticHistoryCollection];
    }
    return statisticHistoryCollection;
  }

  protected convertDateFromClient<T extends IStatisticHistory | NewStatisticHistory | PartialUpdateStatisticHistory>(
    statisticHistory: T,
  ): RestOf<T> {
    return {
      ...statisticHistory,
      startDate: statisticHistory.startDate?.toJSON() ?? null,
      endDate: statisticHistory.endDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restStatisticHistory: RestStatisticHistory): IStatisticHistory {
    return {
      ...restStatisticHistory,
      startDate: restStatisticHistory.startDate ? dayjs(restStatisticHistory.startDate) : undefined,
      endDate: restStatisticHistory.endDate ? dayjs(restStatisticHistory.endDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestStatisticHistory>): HttpResponse<IStatisticHistory> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestStatisticHistory[]>): HttpResponse<IStatisticHistory[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
