import { IStatistic } from 'app/entities/statistic/statistic.model';

export interface ITask {
  id: number;
  title?: string | null;
  description?: string | null;
  statistics?: Pick<IStatistic, 'id'>[] | null;
}

export type NewTask = Omit<ITask, 'id'> & { id: null };
