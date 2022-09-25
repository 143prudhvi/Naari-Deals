import { INotificationType, NewNotificationType } from './notification-type.model';

export const sampleWithRequiredData: INotificationType = {
  id: 991,
};

export const sampleWithPartialData: INotificationType = {
  id: 3951,
  description: 'Chicken white Global',
};

export const sampleWithFullData: INotificationType = {
  id: 18238,
  type: 'back-end',
  description: 'payment Seamless eyeballs',
};

export const sampleWithNewData: NewNotificationType = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
