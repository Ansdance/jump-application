import { ITournament, NewTournament } from './tournament.model';

export const sampleWithRequiredData: ITournament = {
  id: 885,
  tournamentName: 'hydrocarb embezzle pillbox',
};

export const sampleWithPartialData: ITournament = {
  id: 24283,
  tournamentName: 'pocket shrilly',
};

export const sampleWithFullData: ITournament = {
  id: 5814,
  tournamentName: 'crest lining elope',
};

export const sampleWithNewData: NewTournament = {
  tournamentName: 'than aggressive foretell',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
