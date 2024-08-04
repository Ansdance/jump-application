import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IStatistic, NewStatistic } from '../statistic.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IStatistic for edit and NewStatisticFormGroupInput for create.
 */
type StatisticFormGroupInput = IStatistic | PartialWithRequiredKeyOf<NewStatistic>;

type StatisticFormDefaults = Pick<NewStatistic, 'id' | 'tasks'>;

type StatisticFormGroupContent = {
  id: FormControl<IStatistic['id'] | NewStatistic['id']>;
  statisticTitle: FormControl<IStatistic['statisticTitle']>;
  tasks: FormControl<IStatistic['tasks']>;
  employee: FormControl<IStatistic['employee']>;
};

export type StatisticFormGroup = FormGroup<StatisticFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class StatisticFormService {
  createStatisticFormGroup(statistic: StatisticFormGroupInput = { id: null }): StatisticFormGroup {
    const statisticRawValue = {
      ...this.getFormDefaults(),
      ...statistic,
    };
    return new FormGroup<StatisticFormGroupContent>({
      id: new FormControl(
        { value: statisticRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      statisticTitle: new FormControl(statisticRawValue.statisticTitle),
      tasks: new FormControl(statisticRawValue.tasks ?? []),
      employee: new FormControl(statisticRawValue.employee),
    });
  }

  getStatistic(form: StatisticFormGroup): IStatistic | NewStatistic {
    return form.getRawValue() as IStatistic | NewStatistic;
  }

  resetForm(form: StatisticFormGroup, statistic: StatisticFormGroupInput): void {
    const statisticRawValue = { ...this.getFormDefaults(), ...statistic };
    form.reset(
      {
        ...statisticRawValue,
        id: { value: statisticRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): StatisticFormDefaults {
    return {
      id: null,
      tasks: [],
    };
  }
}
