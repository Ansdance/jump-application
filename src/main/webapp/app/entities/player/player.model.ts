import { ITeam } from 'app/entities/team/team.model';

export interface IPlayer {
  id: number;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  manager?: Pick<IPlayer, 'id'> | null;
  team?: Pick<ITeam, 'id'> | null;
}

export type NewPlayer = Omit<IPlayer, 'id'> & { id: null };
