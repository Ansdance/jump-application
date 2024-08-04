import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../statistic-history.test-samples';

import { StatisticHistoryFormService } from './statistic-history-form.service';

describe('StatisticHistory Form Service', () => {
  let service: StatisticHistoryFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StatisticHistoryFormService);
  });

  describe('Service methods', () => {
    describe('createStatisticHistoryFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createStatisticHistoryFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            language: expect.any(Object),
            statistic: expect.any(Object),
            department: expect.any(Object),
            employee: expect.any(Object),
          }),
        );
      });

      it('passing IStatisticHistory should create a new form with FormGroup', () => {
        const formGroup = service.createStatisticHistoryFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            language: expect.any(Object),
            statistic: expect.any(Object),
            department: expect.any(Object),
            employee: expect.any(Object),
          }),
        );
      });
    });

    describe('getStatisticHistory', () => {
      it('should return NewStatisticHistory for default StatisticHistory initial value', () => {
        const formGroup = service.createStatisticHistoryFormGroup(sampleWithNewData);

        const statisticHistory = service.getStatisticHistory(formGroup) as any;

        expect(statisticHistory).toMatchObject(sampleWithNewData);
      });

      it('should return NewStatisticHistory for empty StatisticHistory initial value', () => {
        const formGroup = service.createStatisticHistoryFormGroup();

        const statisticHistory = service.getStatisticHistory(formGroup) as any;

        expect(statisticHistory).toMatchObject({});
      });

      it('should return IStatisticHistory', () => {
        const formGroup = service.createStatisticHistoryFormGroup(sampleWithRequiredData);

        const statisticHistory = service.getStatisticHistory(formGroup) as any;

        expect(statisticHistory).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IStatisticHistory should not enable id FormControl', () => {
        const formGroup = service.createStatisticHistoryFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewStatisticHistory should disable id FormControl', () => {
        const formGroup = service.createStatisticHistoryFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
