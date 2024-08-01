import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../statistic.test-samples';

import { StatisticFormService } from './statistic-form.service';

describe('Statistic Form Service', () => {
  let service: StatisticFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StatisticFormService);
  });

  describe('Service methods', () => {
    describe('createStatisticFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createStatisticFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            statisticTitle: expect.any(Object),
            tasks: expect.any(Object),
            employee: expect.any(Object),
          }),
        );
      });

      it('passing IStatistic should create a new form with FormGroup', () => {
        const formGroup = service.createStatisticFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            statisticTitle: expect.any(Object),
            tasks: expect.any(Object),
            employee: expect.any(Object),
          }),
        );
      });
    });

    describe('getStatistic', () => {
      it('should return NewStatistic for default Statistic initial value', () => {
        const formGroup = service.createStatisticFormGroup(sampleWithNewData);

        const statistic = service.getStatistic(formGroup) as any;

        expect(statistic).toMatchObject(sampleWithNewData);
      });

      it('should return NewStatistic for empty Statistic initial value', () => {
        const formGroup = service.createStatisticFormGroup();

        const statistic = service.getStatistic(formGroup) as any;

        expect(statistic).toMatchObject({});
      });

      it('should return IStatistic', () => {
        const formGroup = service.createStatisticFormGroup(sampleWithRequiredData);

        const statistic = service.getStatistic(formGroup) as any;

        expect(statistic).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IStatistic should not enable id FormControl', () => {
        const formGroup = service.createStatisticFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewStatistic should disable id FormControl', () => {
        const formGroup = service.createStatisticFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
