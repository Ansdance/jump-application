import { ILocation } from 'app/entities/location/location.model';
import { ITeam } from 'app/entities/team/team.model';

export interface ITournament {
  id: number;
  tournamentName?: string | null;
  location?: Pick<ILocation, 'id'> | null;
  teams?: Pick<ITeam, 'id'>[] | null;
}

export type NewTournament = Omit<ITournament, 'id'> & { id: null };
