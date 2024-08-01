import { ITask } from 'app/entities/task/task.model';
import { IPlayer } from 'app/entities/player/player.model';

export interface IStatistic {
  id: number;
  statisticTitle?: string | null;
  tasks?: Pick<ITask, 'id' | 'title'>[] | null;
  employee?: Pick<IPlayer, 'id'> | null;
}

export type NewStatistic = Omit<IStatistic, 'id'> & { id: null };
