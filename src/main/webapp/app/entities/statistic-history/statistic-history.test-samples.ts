import dayjs from 'dayjs/esm';

import { IStatisticHistory, NewStatisticHistory } from './statistic-history.model';

export const sampleWithRequiredData: IStatisticHistory = {
  id: 18281,
};

export const sampleWithPartialData: IStatisticHistory = {
  id: 30097,
  startDate: dayjs('2024-08-04T10:16'),
  endDate: dayjs('2024-08-04T15:22'),
  language: 'FRENCH',
};

export const sampleWithFullData: IStatisticHistory = {
  id: 32389,
  startDate: dayjs('2024-08-04T08:56'),
  endDate: dayjs('2024-08-04T11:27'),
  language: 'FRENCH',
};

export const sampleWithNewData: NewStatisticHistory = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
