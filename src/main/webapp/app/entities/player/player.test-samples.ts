import { IPlayer, NewPlayer } from './player.model';

export const sampleWithRequiredData: IPlayer = {
  id: 20424,
};

export const sampleWithPartialData: IPlayer = {
  id: 14754,
  email: 'Gedeon.Wehner97@ya.ru',
};

export const sampleWithFullData: IPlayer = {
  id: 9774,
  firstName: 'Алевтина',
  lastName: 'Lowe',
  email: 'Nifont91@gmail.com',
  phoneNumber: 'feminine down about',
};

export const sampleWithNewData: NewPlayer = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
