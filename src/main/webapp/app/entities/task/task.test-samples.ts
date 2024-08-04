import { ITask, NewTask } from './task.model';

export const sampleWithRequiredData: ITask = {
  id: 7877,
};

export const sampleWithPartialData: ITask = {
  id: 15545,
  title: 'bah',
};

export const sampleWithFullData: ITask = {
  id: 11960,
  title: 'legal like',
  description: 'consequently',
};

export const sampleWithNewData: NewTask = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
