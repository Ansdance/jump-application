import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'jumpApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'region',
    data: { pageTitle: 'jumpApp.region.home.title' },
    loadChildren: () => import('./region/region.routes'),
  },
  {
    path: 'country',
    data: { pageTitle: 'jumpApp.country.home.title' },
    loadChildren: () => import('./country/country.routes'),
  },
  {
    path: 'location',
    data: { pageTitle: 'jumpApp.location.home.title' },
    loadChildren: () => import('./location/location.routes'),
  },
  {
    path: 'tournament',
    data: { pageTitle: 'jumpApp.tournament.home.title' },
    loadChildren: () => import('./tournament/tournament.routes'),
  },
  {
    path: 'task',
    data: { pageTitle: 'jumpApp.task.home.title' },
    loadChildren: () => import('./task/task.routes'),
  },
  {
    path: 'player',
    data: { pageTitle: 'jumpApp.player.home.title' },
    loadChildren: () => import('./player/player.routes'),
  },
  {
    path: 'statistic',
    data: { pageTitle: 'jumpApp.statistic.home.title' },
    loadChildren: () => import('./statistic/statistic.routes'),
  },
  {
    path: 'statistic-history',
    data: { pageTitle: 'jumpApp.statisticHistory.home.title' },
    loadChildren: () => import('./statistic-history/statistic-history.routes'),
  },
  {
    path: 'team',
    data: { pageTitle: 'jumpApp.team.home.title' },
    loadChildren: () => import('./team/team.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
