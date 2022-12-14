import { IFeedback, NewFeedback } from './feedback.model';

export const sampleWithRequiredData: IFeedback = {
  id: 38124,
};

export const sampleWithPartialData: IFeedback = {
  id: 42047,
  name: 'lavender Cloned',
  email: 'Wade67@yahoo.com',
  phone: '374.475.5685',
  userId: 'functionalities',
  message: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IFeedback = {
  id: 75447,
  type: 'virtual Corporate',
  name: 'Licensed',
  email: 'Aliya.Weimann@hotmail.com',
  phone: '1-421-800-7462',
  userId: 'e-enable',
  message: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewFeedback = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
