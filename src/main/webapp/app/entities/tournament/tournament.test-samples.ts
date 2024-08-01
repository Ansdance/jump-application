import { ITournament, NewTournament } from './tournament.model';

export const sampleWithRequiredData: ITournament = {
  id: 4899,
  tournamentName: 'demerge once likely',
};

export const sampleWithPartialData: ITournament = {
  id: 2771,
  tournamentName: 'penalise',
};

export const sampleWithFullData: ITournament = {
  id: 32253,
  tournamentName: 'center instead eek',
};

export const sampleWithNewData: NewTournament = {
  tournamentName: 'collision',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
