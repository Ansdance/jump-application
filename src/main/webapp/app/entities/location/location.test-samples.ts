import { ILocation, NewLocation } from './location.model';

export const sampleWithRequiredData: ILocation = {
  id: 17061,
};

export const sampleWithPartialData: ILocation = {
  id: 23799,
  city: 'Сосьва (Хант.)',
};

export const sampleWithFullData: ILocation = {
  id: 4332,
  streetAddress: 'and thankfully',
  postalCode: 'through so',
  city: 'Мостовской',
  stateProvince: 'involve',
};

export const sampleWithNewData: NewLocation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
