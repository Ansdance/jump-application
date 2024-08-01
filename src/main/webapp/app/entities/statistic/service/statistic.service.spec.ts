import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IStatistic } from '../statistic.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../statistic.test-samples';

import { StatisticService } from './statistic.service';

const requireRestSample: IStatistic = {
  ...sampleWithRequiredData,
};

describe('Statistic Service', () => {
  let service: StatisticService;
  let httpMock: HttpTestingController;
  let expectedResult: IStatistic | IStatistic[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(StatisticService);
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

    it('should create a Statistic', () => {
      const statistic = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(statistic).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Statistic', () => {
      const statistic = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(statistic).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Statistic', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Statistic', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Statistic', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addStatisticToCollectionIfMissing', () => {
      it('should add a Statistic to an empty array', () => {
        const statistic: IStatistic = sampleWithRequiredData;
        expectedResult = service.addStatisticToCollectionIfMissing([], statistic);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(statistic);
      });

      it('should not add a Statistic to an array that contains it', () => {
        const statistic: IStatistic = sampleWithRequiredData;
        const statisticCollection: IStatistic[] = [
          {
            ...statistic,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addStatisticToCollectionIfMissing(statisticCollection, statistic);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Statistic to an array that doesn't contain it", () => {
        const statistic: IStatistic = sampleWithRequiredData;
        const statisticCollection: IStatistic[] = [sampleWithPartialData];
        expectedResult = service.addStatisticToCollectionIfMissing(statisticCollection, statistic);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(statistic);
      });

      it('should add only unique Statistic to an array', () => {
        const statisticArray: IStatistic[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const statisticCollection: IStatistic[] = [sampleWithRequiredData];
        expectedResult = service.addStatisticToCollectionIfMissing(statisticCollection, ...statisticArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const statistic: IStatistic = sampleWithRequiredData;
        const statistic2: IStatistic = sampleWithPartialData;
        expectedResult = service.addStatisticToCollectionIfMissing([], statistic, statistic2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(statistic);
        expect(expectedResult).toContain(statistic2);
      });

      it('should accept null and undefined values', () => {
        const statistic: IStatistic = sampleWithRequiredData;
        expectedResult = service.addStatisticToCollectionIfMissing([], null, statistic, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(statistic);
      });

      it('should return initial array if no Statistic is added', () => {
        const statisticCollection: IStatistic[] = [sampleWithRequiredData];
        expectedResult = service.addStatisticToCollectionIfMissing(statisticCollection, undefined, null);
        expect(expectedResult).toEqual(statisticCollection);
      });
    });

    describe('compareStatistic', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareStatistic(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareStatistic(entity1, entity2);
        const compareResult2 = service.compareStatistic(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareStatistic(entity1, entity2);
        const compareResult2 = service.compareStatistic(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareStatistic(entity1, entity2);
        const compareResult2 = service.compareStatistic(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
