import { IDealType, NewDealType } from './deal-type.model';

export const sampleWithRequiredData: IDealType = {
  id: 11532,
};

export const sampleWithPartialData: IDealType = {
  id: 871,
  title: 'California Meadows orchestrate',
  subTitle: 'architectures',
  country: 'Saint Kitts and Nevis',
};

export const sampleWithFullData: IDealType = {
  id: 34231,
  title: 'Re-engineered enterprise navigate',
  subTitle: 'index',
  icon: 'Borders ubiquitous Credit',
  bgColor: 'adapter Frozen',
  country: 'Switzerland',
  code: 'Home',
  status: 'capacitor invoice',
};

export const sampleWithNewData: NewDealType = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
