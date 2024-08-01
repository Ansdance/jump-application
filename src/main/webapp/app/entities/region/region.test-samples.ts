import { IRegion, NewRegion } from './region.model';

export const sampleWithRequiredData: IRegion = {
  id: 19328,
};

export const sampleWithPartialData: IRegion = {
  id: 20885,
};

export const sampleWithFullData: IRegion = {
  id: 7862,
  regionName: 'trammel aw',
};

export const sampleWithNewData: NewRegion = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
