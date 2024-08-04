import { ICountry, NewCountry } from './country.model';

export const sampleWithRequiredData: ICountry = {
  id: 8201,
};

export const sampleWithPartialData: ICountry = {
  id: 3677,
  countryName: 'short',
};

export const sampleWithFullData: ICountry = {
  id: 9891,
  countryName: 'capacity',
};

export const sampleWithNewData: NewCountry = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
