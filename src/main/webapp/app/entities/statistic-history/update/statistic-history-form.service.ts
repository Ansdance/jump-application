import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IStatisticHistory, NewStatisticHistory } from '../statistic-history.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IStatisticHistory for edit and NewStatisticHistoryFormGroupInput for create.
 */
type StatisticHistoryFormGroupInput = IStatisticHistory | PartialWithRequiredKeyOf<NewStatisticHistory>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IStatisticHistory | NewStatisticHistory> = Omit<T, 'startDate' | 'endDate'> & {
  startDate?: string | null;
  endDate?: string | null;
};

type StatisticHistoryFormRawValue = FormValueOf<IStatisticHistory>;

type NewStatisticHistoryFormRawValue = FormValueOf<NewStatisticHistory>;

type StatisticHistoryFormDefaults = Pick<NewStatisticHistory, 'id' | 'startDate' | 'endDate'>;

type StatisticHistoryFormGroupContent = {
  id: FormControl<StatisticHistoryFormRawValue['id'] | NewStatisticHistory['id']>;
  startDate: FormControl<StatisticHistoryFormRawValue['startDate']>;
  endDate: FormControl<StatisticHistoryFormRawValue['endDate']>;
  language: FormControl<StatisticHistoryFormRawValue['language']>;
  statistic: FormControl<StatisticHistoryFormRawValue['statistic']>;
  department: FormControl<StatisticHistoryFormRawValue['department']>;
  employee: FormControl<StatisticHistoryFormRawValue['employee']>;
};

export type StatisticHistoryFormGroup = FormGroup<StatisticHistoryFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class StatisticHistoryFormService {
  createStatisticHistoryFormGroup(statisticHistory: StatisticHistoryFormGroupInput = { id: null }): StatisticHistoryFormGroup {
    const statisticHistoryRawValue = this.convertStatisticHistoryToStatisticHistoryRawValue({
      ...this.getFormDefaults(),
      ...statisticHistory,
    });
    return new FormGroup<StatisticHistoryFormGroupContent>({
      id: new FormControl(
        { value: statisticHistoryRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      startDate: new FormControl(statisticHistoryRawValue.startDate),
      endDate: new FormControl(statisticHistoryRawValue.endDate),
      language: new FormControl(statisticHistoryRawValue.language),
      statistic: new FormControl(statisticHistoryRawValue.statistic),
      department: new FormControl(statisticHistoryRawValue.department),
      employee: new FormControl(statisticHistoryRawValue.employee),
    });
  }

  getStatisticHistory(form: StatisticHistoryFormGroup): IStatisticHistory | NewStatisticHistory {
    return this.convertStatisticHistoryRawValueToStatisticHistory(
      form.getRawValue() as StatisticHistoryFormRawValue | NewStatisticHistoryFormRawValue,
    );
  }

  resetForm(form: StatisticHistoryFormGroup, statisticHistory: StatisticHistoryFormGroupInput): void {
    const statisticHistoryRawValue = this.convertStatisticHistoryToStatisticHistoryRawValue({
      ...this.getFormDefaults(),
      ...statisticHistory,
    });
    form.reset(
      {
        ...statisticHistoryRawValue,
        id: { value: statisticHistoryRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): StatisticHistoryFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      startDate: currentTime,
      endDate: currentTime,
    };
  }

  private convertStatisticHistoryRawValueToStatisticHistory(
    rawStatisticHistory: StatisticHistoryFormRawValue | NewStatisticHistoryFormRawValue,
  ): IStatisticHistory | NewStatisticHistory {
    return {
      ...rawStatisticHistory,
      startDate: dayjs(rawStatisticHistory.startDate, DATE_TIME_FORMAT),
      endDate: dayjs(rawStatisticHistory.endDate, DATE_TIME_FORMAT),
    };
  }

  private convertStatisticHistoryToStatisticHistoryRawValue(
    statisticHistory: IStatisticHistory | (Partial<NewStatisticHistory> & StatisticHistoryFormDefaults),
  ): StatisticHistoryFormRawValue | PartialWithRequiredKeyOf<NewStatisticHistoryFormRawValue> {
    return {
      ...statisticHistory,
      startDate: statisticHistory.startDate ? statisticHistory.startDate.format(DATE_TIME_FORMAT) : undefined,
      endDate: statisticHistory.endDate ? statisticHistory.endDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
