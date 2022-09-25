import { IEmailSubscription, NewEmailSubscription } from './email-subscription.model';

export const sampleWithRequiredData: IEmailSubscription = {
  id: 37961,
};

export const sampleWithPartialData: IEmailSubscription = {
  id: 35228,
  email: 'Ivah.Schuster@hotmail.com',
};

export const sampleWithFullData: IEmailSubscription = {
  id: 23363,
  email: 'Della_Goyette97@gmail.com',
  country: 'Aruba',
};

export const sampleWithNewData: NewEmailSubscription = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
