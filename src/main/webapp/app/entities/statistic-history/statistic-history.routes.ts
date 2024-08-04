import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { StatisticHistoryComponent } from './list/statistic-history.component';
import { StatisticHistoryDetailComponent } from './detail/statistic-history-detail.component';
import { StatisticHistoryUpdateComponent } from './update/statistic-history-update.component';
import StatisticHistoryResolve from './route/statistic-history-routing-resolve.service';

const statisticHistoryRoute: Routes = [
  {
    path: '',
    component: StatisticHistoryComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StatisticHistoryDetailComponent,
    resolve: {
      statisticHistory: StatisticHistoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StatisticHistoryUpdateComponent,
    resolve: {
      statisticHistory: StatisticHistoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StatisticHistoryUpdateComponent,
    resolve: {
      statisticHistory: StatisticHistoryResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default statisticHistoryRoute;
