import { INotification, NewNotification } from './notification.model';

export const sampleWithRequiredData: INotification = {
  id: 30621,
};

export const sampleWithPartialData: INotification = {
  id: 16360,
  message: 'Customizable synergies',
  status: 'quantify magenta',
  type: 'Incredible',
  dateOfRead: 'generate Buckinghamshire',
};

export const sampleWithFullData: INotification = {
  id: 87572,
  userId: 'Market hacking Wyoming',
  title: 'tan',
  message: 'Gorgeous Savings',
  status: 'Representative cyan',
  type: 'Books Berkshire',
  dateOfRead: 'Mobility',
  imageUrl: '../fake-data/blob/hipster.txt',
  originalPrice: 'Cotton Fresh Public-key',
  currentPrice: 'navigate Solutions',
  discount: 'calculate Palestinian microchip',
  discountType: 'Games instruction',
};

export const sampleWithNewData: NewNotification = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
