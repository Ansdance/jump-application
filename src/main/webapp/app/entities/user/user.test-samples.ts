import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 29175,
  login: 'ybX@P\\$Xi\\JlU2P\\-l',
};

export const sampleWithPartialData: IUser = {
  id: 23400,
  login: 'qcvU',
};

export const sampleWithFullData: IUser = {
  id: 12503,
  login: 'UV',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
