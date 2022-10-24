import { IMerchant, NewMerchant } from './merchant.model';

export const sampleWithRequiredData: IMerchant = {
  id: 39951,
};

export const sampleWithPartialData: IMerchant = {
  id: 29592,
  country: 'Senegal',
  storeIcon: '../fake-data/blob/hipster.txt',
  type: 'Market Legacy Cambridgeshire',
  location: 'Hawaii Customer Sausages',
  siteUrl: 'Operations Branch Shoes',
};

export const sampleWithFullData: IMerchant = {
  id: 57040,
  name: 'Ergonomic Pound',
  country: 'Taiwan',
  city: 'Considinemouth',
  storeIcon: '../fake-data/blob/hipster.txt',
  type: 'Bedfordshire Tactics Maine',
  location: 'Congolese program Central',
  siteUrl: 'navigating Buckinghamshire',
};

export const sampleWithNewData: NewMerchant = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
