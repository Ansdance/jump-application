import { ILocation } from 'app/entities/location/location.model';

export interface ITournament {
  id: number;
  tournamentName?: string | null;
  location?: Pick<ILocation, 'id'> | null;
}

export type NewTournament = Omit<ITournament, 'id'> & { id: null };
