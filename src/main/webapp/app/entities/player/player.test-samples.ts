import { IPlayer, NewPlayer } from './player.model';

export const sampleWithRequiredData: IPlayer = {
  id: 2229,
};

export const sampleWithPartialData: IPlayer = {
  id: 9318,
  lastName: 'Jakubowski',
  email: 'Lyubomir.Kuphal@mail.ru',
  phoneNumber: 'thankfully unto',
};

export const sampleWithFullData: IPlayer = {
  id: 2536,
  firstName: 'Рубен',
  lastName: 'Marks',
  email: 'Kallistrat7@mail.ru',
  phoneNumber: 'prevaricate',
};

export const sampleWithNewData: NewPlayer = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
