import { IMerchant, NewMerchant } from './merchant.model';

export const sampleWithRequiredData: IMerchant = {
  id: 39951,
};

export const sampleWithPartialData: IMerchant = {
  id: 43436,
  title: 'back Infrastructure Hawaii',
  address: 'Cape Factors Operations',
  phone: '(240) 725-5005 x679',
  country: 'Taiwan',
  city: 'Considinemouth',
  type: 'Bedfordshire Tactics Maine',
  location: 'Congolese program Central',
  siteUrl: 'navigating Buckinghamshire',
};

export const sampleWithFullData: IMerchant = {
  id: 60904,
  code: 'Dominica',
  title: 'withdrawal Islands Account',
  subTitle: 'lime background',
  address: 'payment',
  phone: '1-355-734-0052 x8712',
  country: 'Serbia',
  city: 'MacGyverview',
  imageUrl: 'monitoring port Account',
  type: 'dot-com',
  location: 'overriding Investment',
  siteUrl: 'Rubber HDD deploy',
  status: 'edge',
};

export const sampleWithNewData: NewMerchant = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
