import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: 'ca2a3c52-f2b0-4eba-b170-f5e439eeaa92',
};

export const sampleWithPartialData: IAuthority = {
  name: '29a4de24-275b-487e-b33c-5ecdfecea130',
};

export const sampleWithFullData: IAuthority = {
  name: 'eaa57257-d0e4-4354-a78e-2269f90da37f',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
