import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IStatisticHistory } from '../statistic-history.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../statistic-history.test-samples';

import { StatisticHistoryService, RestStatisticHistory } from './statistic-history.service';

const requireRestSample: RestStatisticHistory = {
  ...sampleWithRequiredData,
  startDate: sampleWithRequiredData.startDate?.toJSON(),
  endDate: sampleWithRequiredData.endDate?.toJSON(),
};

describe('StatisticHistory Service', () => {
  let service: StatisticHistoryService;
  let httpMock: HttpTestingController;
  let expectedResult: IStatisticHistory | IStatisticHistory[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(StatisticHistoryService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a StatisticHistory', () => {
      const statisticHistory = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(statisticHistory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a StatisticHistory', () => {
      const statisticHistory = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(statisticHistory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a StatisticHistory', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of StatisticHistory', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a StatisticHistory', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addStatisticHistoryToCollectionIfMissing', () => {
      it('should add a StatisticHistory to an empty array', () => {
        const statisticHistory: IStatisticHistory = sampleWithRequiredData;
        expectedResult = service.addStatisticHistoryToCollectionIfMissing([], statisticHistory);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(statisticHistory);
      });

      it('should not add a StatisticHistory to an array that contains it', () => {
        const statisticHistory: IStatisticHistory = sampleWithRequiredData;
        const statisticHistoryCollection: IStatisticHistory[] = [
          {
            ...statisticHistory,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addStatisticHistoryToCollectionIfMissing(statisticHistoryCollection, statisticHistory);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a StatisticHistory to an array that doesn't contain it", () => {
        const statisticHistory: IStatisticHistory = sampleWithRequiredData;
        const statisticHistoryCollection: IStatisticHistory[] = [sampleWithPartialData];
        expectedResult = service.addStatisticHistoryToCollectionIfMissing(statisticHistoryCollection, statisticHistory);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(statisticHistory);
      });

      it('should add only unique StatisticHistory to an array', () => {
        const statisticHistoryArray: IStatisticHistory[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const statisticHistoryCollection: IStatisticHistory[] = [sampleWithRequiredData];
        expectedResult = service.addStatisticHistoryToCollectionIfMissing(statisticHistoryCollection, ...statisticHistoryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const statisticHistory: IStatisticHistory = sampleWithRequiredData;
        const statisticHistory2: IStatisticHistory = sampleWithPartialData;
        expectedResult = service.addStatisticHistoryToCollectionIfMissing([], statisticHistory, statisticHistory2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(statisticHistory);
        expect(expectedResult).toContain(statisticHistory2);
      });

      it('should accept null and undefined values', () => {
        const statisticHistory: IStatisticHistory = sampleWithRequiredData;
        expectedResult = service.addStatisticHistoryToCollectionIfMissing([], null, statisticHistory, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(statisticHistory);
      });

      it('should return initial array if no StatisticHistory is added', () => {
        const statisticHistoryCollection: IStatisticHistory[] = [sampleWithRequiredData];
        expectedResult = service.addStatisticHistoryToCollectionIfMissing(statisticHistoryCollection, undefined, null);
        expect(expectedResult).toEqual(statisticHistoryCollection);
      });
    });

    describe('compareStatisticHistory', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareStatisticHistory(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareStatisticHistory(entity1, entity2);
        const compareResult2 = service.compareStatisticHistory(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareStatisticHistory(entity1, entity2);
        const compareResult2 = service.compareStatisticHistory(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareStatisticHistory(entity1, entity2);
        const compareResult2 = service.compareStatisticHistory(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
