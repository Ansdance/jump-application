import { ITask, NewTask } from './task.model';

export const sampleWithRequiredData: ITask = {
  id: 2408,
};

export const sampleWithPartialData: ITask = {
  id: 912,
  title: 'ack',
  description: 'lively vast',
};

export const sampleWithFullData: ITask = {
  id: 22486,
  title: 'apud empty caution',
  description: 'who badger',
};

export const sampleWithNewData: NewTask = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
