import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IStatisticHistory } from '../statistic-history.model';
import { StatisticHistoryService } from '../service/statistic-history.service';

import statisticHistoryResolve from './statistic-history-routing-resolve.service';

describe('StatisticHistory routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: StatisticHistoryService;
  let resultStatisticHistory: IStatisticHistory | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(StatisticHistoryService);
    resultStatisticHistory = undefined;
  });

  describe('resolve', () => {
    it('should return IStatisticHistory returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        statisticHistoryResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultStatisticHistory = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultStatisticHistory).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        statisticHistoryResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultStatisticHistory = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultStatisticHistory).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IStatisticHistory>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        statisticHistoryResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultStatisticHistory = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultStatisticHistory).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
