import { ITournament } from 'app/entities/tournament/tournament.model';

export interface IPlayer {
  id: number;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  manager?: Pick<IPlayer, 'id'> | null;
  tournament?: Pick<ITournament, 'id'> | null;
}

export type NewPlayer = Omit<IPlayer, 'id'> & { id: null };
