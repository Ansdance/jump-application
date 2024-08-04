import dayjs from 'dayjs/esm';
import { IStatistic } from 'app/entities/statistic/statistic.model';
import { ITournament } from 'app/entities/tournament/tournament.model';
import { IPlayer } from 'app/entities/player/player.model';
import { Language } from 'app/entities/enumerations/language.model';

export interface IStatisticHistory {
  id: number;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  language?: keyof typeof Language | null;
  statistic?: Pick<IStatistic, 'id'> | null;
  department?: Pick<ITournament, 'id'> | null;
  employee?: Pick<IPlayer, 'id'> | null;
}

export type NewStatisticHistory = Omit<IStatisticHistory, 'id'> & { id: null };
