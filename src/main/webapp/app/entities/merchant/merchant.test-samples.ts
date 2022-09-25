import { IMerchant, NewMerchant } from './merchant.model';

export const sampleWithRequiredData: IMerchant = {
  id: 39951,
};

export const sampleWithPartialData: IMerchant = {
  id: 62419,
  country: 'French Guiana',
  storeIcon: '../fake-data/blob/hipster.txt',
  type: 'Investor functionalities Health',
  location: 'Naira Human',
};

export const sampleWithFullData: IMerchant = {
  id: 15862,
  name: 'Factors',
  country: 'Uruguay',
  city: 'Chayaport',
  storeIcon: '../fake-data/blob/hipster.txt',
  type: 'web-readiness',
  location: 'Nebraska',
};

export const sampleWithNewData: NewMerchant = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
