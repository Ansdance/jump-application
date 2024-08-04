import { IRegion, NewRegion } from './region.model';

export const sampleWithRequiredData: IRegion = {
  id: 26024,
};

export const sampleWithPartialData: IRegion = {
  id: 27111,
};

export const sampleWithFullData: IRegion = {
  id: 5805,
  regionName: 'though utterly a',
};

export const sampleWithNewData: NewRegion = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
