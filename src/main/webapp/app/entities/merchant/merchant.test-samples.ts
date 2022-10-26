import { IMerchant, NewMerchant } from './merchant.model';

export const sampleWithRequiredData: IMerchant = {
  id: 39951,
};

export const sampleWithPartialData: IMerchant = {
  id: 29592,
  country: 'Senegal',
  storeIcon: 'Market Legacy Cambridgeshire',
  type: 'Hawaii Customer Sausages',
  location: 'Operations Branch Shoes',
  siteUrl: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IMerchant = {
  id: 57040,
  name: 'Ergonomic Pound',
  country: 'Taiwan',
  city: 'Considinemouth',
  storeIcon: 'Bedfordshire Tactics Maine',
  type: 'Congolese program Central',
  location: 'navigating Buckinghamshire',
  siteUrl: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewMerchant = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
