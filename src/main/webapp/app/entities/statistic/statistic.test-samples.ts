import { IStatistic, NewStatistic } from './statistic.model';

export const sampleWithRequiredData: IStatistic = {
  id: 19809,
};

export const sampleWithPartialData: IStatistic = {
  id: 19231,
};

export const sampleWithFullData: IStatistic = {
  id: 27115,
  statisticTitle: 'yearly',
};

export const sampleWithNewData: NewStatistic = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
