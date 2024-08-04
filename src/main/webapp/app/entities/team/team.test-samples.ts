import { ITeam, NewTeam } from './team.model';

export const sampleWithRequiredData: ITeam = {
  id: 5883,
};

export const sampleWithPartialData: ITeam = {
  id: 20288,
  teamName: 'before',
};

export const sampleWithFullData: ITeam = {
  id: 26404,
  teamName: 'mmm breastplate meh',
};

export const sampleWithNewData: NewTeam = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
