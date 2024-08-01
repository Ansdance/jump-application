import { ICountry, NewCountry } from './country.model';

export const sampleWithRequiredData: ICountry = {
  id: 13743,
};

export const sampleWithPartialData: ICountry = {
  id: 29854,
};

export const sampleWithFullData: ICountry = {
  id: 14203,
  countryName: 'rouge despite',
};

export const sampleWithNewData: NewCountry = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
