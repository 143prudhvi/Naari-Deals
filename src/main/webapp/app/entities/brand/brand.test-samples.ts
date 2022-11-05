import { IBrand, NewBrand } from './brand.model';

export const sampleWithRequiredData: IBrand = {
  id: 69581,
};

export const sampleWithPartialData: IBrand = {
  id: 92165,
  title: 'Legacy impactful',
  subTitle: 'Pizza Suriname',
  code: 'Baby interface Concrete',
  status: 'parse',
  country: 'Turks and Caicos Islands',
};

export const sampleWithFullData: IBrand = {
  id: 95885,
  title: 'haptic',
  subTitle: 'Money Minnesota cross-platform',
  code: 'sensor',
  status: 'invoice Incredible syndicate',
  country: 'Mozambique',
  imageUrl: 'Cambridgeshire toolset Global',
};

export const sampleWithNewData: NewBrand = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
