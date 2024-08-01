import { IStatistic, NewStatistic } from './statistic.model';

export const sampleWithRequiredData: IStatistic = {
  id: 873,
};

export const sampleWithPartialData: IStatistic = {
  id: 729,
};

export const sampleWithFullData: IStatistic = {
  id: 12078,
  statisticTitle: 'phenotype',
};

export const sampleWithNewData: NewStatistic = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
