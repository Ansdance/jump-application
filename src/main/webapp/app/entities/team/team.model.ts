import { ITournament } from 'app/entities/tournament/tournament.model';

export interface ITeam {
  id: number;
  teamName?: string | null;
  tournaments?: Pick<ITournament, 'id'>[] | null;
}

export type NewTeam = Omit<ITeam, 'id'> & { id: null };
