import dayjs from 'dayjs/esm';

import { IStatisticHistory, NewStatisticHistory } from './statistic-history.model';

export const sampleWithRequiredData: IStatisticHistory = {
  id: 15491,
};

export const sampleWithPartialData: IStatisticHistory = {
  id: 26068,
  startDate: dayjs('2024-08-01T14:37'),
  language: 'SPANISH',
};

export const sampleWithFullData: IStatisticHistory = {
  id: 5585,
  startDate: dayjs('2024-08-01T14:02'),
  endDate: dayjs('2024-08-01T06:02'),
  language: 'FRENCH',
};

export const sampleWithNewData: NewStatisticHistory = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
