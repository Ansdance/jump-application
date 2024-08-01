import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IStatistic } from '../statistic.model';
import { StatisticService } from '../service/statistic.service';

const statisticResolve = (route: ActivatedRouteSnapshot): Observable<null | IStatistic> => {
  const id = route.params['id'];
  if (id) {
    return inject(StatisticService)
      .find(id)
      .pipe(
        mergeMap((statistic: HttpResponse<IStatistic>) => {
          if (statistic.body) {
            return of(statistic.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default statisticResolve;
