import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IStatisticHistory } from '../statistic-history.model';
import { StatisticHistoryService } from '../service/statistic-history.service';

const statisticHistoryResolve = (route: ActivatedRouteSnapshot): Observable<null | IStatisticHistory> => {
  const id = route.params['id'];
  if (id) {
    return inject(StatisticHistoryService)
      .find(id)
      .pipe(
        mergeMap((statisticHistory: HttpResponse<IStatisticHistory>) => {
          if (statisticHistory.body) {
            return of(statisticHistory.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default statisticHistoryResolve;
