import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStatistic, NewStatistic } from '../statistic.model';

export type PartialUpdateStatistic = Partial<IStatistic> & Pick<IStatistic, 'id'>;

export type EntityResponseType = HttpResponse<IStatistic>;
export type EntityArrayResponseType = HttpResponse<IStatistic[]>;

@Injectable({ providedIn: 'root' })
export class StatisticService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/statistics');

  create(statistic: NewStatistic): Observable<EntityResponseType> {
    return this.http.post<IStatistic>(this.resourceUrl, statistic, { observe: 'response' });
  }

  update(statistic: IStatistic): Observable<EntityResponseType> {
    return this.http.put<IStatistic>(`${this.resourceUrl}/${this.getStatisticIdentifier(statistic)}`, statistic, { observe: 'response' });
  }

  partialUpdate(statistic: PartialUpdateStatistic): Observable<EntityResponseType> {
    return this.http.patch<IStatistic>(`${this.resourceUrl}/${this.getStatisticIdentifier(statistic)}`, statistic, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStatistic>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStatistic[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getStatisticIdentifier(statistic: Pick<IStatistic, 'id'>): number {
    return statistic.id;
  }

  compareStatistic(o1: Pick<IStatistic, 'id'> | null, o2: Pick<IStatistic, 'id'> | null): boolean {
    return o1 && o2 ? this.getStatisticIdentifier(o1) === this.getStatisticIdentifier(o2) : o1 === o2;
  }

  addStatisticToCollectionIfMissing<Type extends Pick<IStatistic, 'id'>>(
    statisticCollection: Type[],
    ...statisticsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const statistics: Type[] = statisticsToCheck.filter(isPresent);
    if (statistics.length > 0) {
      const statisticCollectionIdentifiers = statisticCollection.map(statisticItem => this.getStatisticIdentifier(statisticItem));
      const statisticsToAdd = statistics.filter(statisticItem => {
        const statisticIdentifier = this.getStatisticIdentifier(statisticItem);
        if (statisticCollectionIdentifiers.includes(statisticIdentifier)) {
          return false;
        }
        statisticCollectionIdentifiers.push(statisticIdentifier);
        return true;
      });
      return [...statisticsToAdd, ...statisticCollection];
    }
    return statisticCollection;
  }
}
