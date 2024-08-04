import { ILocation, NewLocation } from './location.model';

export const sampleWithRequiredData: ILocation = {
  id: 30085,
};

export const sampleWithPartialData: ILocation = {
  id: 18913,
  stateProvince: 'suspicious with save',
};

export const sampleWithFullData: ILocation = {
  id: 25081,
  streetAddress: 'during fossilise philosophy',
  postalCode: 'near pfft',
  city: 'Адлер',
  stateProvince: 'unto',
};

export const sampleWithNewData: NewLocation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
