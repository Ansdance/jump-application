import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { StatisticComponent } from './list/statistic.component';
import { StatisticDetailComponent } from './detail/statistic-detail.component';
import { StatisticUpdateComponent } from './update/statistic-update.component';
import StatisticResolve from './route/statistic-routing-resolve.service';

const statisticRoute: Routes = [
  {
    path: '',
    component: StatisticComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StatisticDetailComponent,
    resolve: {
      statistic: StatisticResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StatisticUpdateComponent,
    resolve: {
      statistic: StatisticResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StatisticUpdateComponent,
    resolve: {
      statistic: StatisticResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default statisticRoute;
