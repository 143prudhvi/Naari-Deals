import { ICategory, NewCategory } from './category.model';

export const sampleWithRequiredData: ICategory = {
  id: 2529,
};

export const sampleWithPartialData: ICategory = {
  id: 81675,
  parent: 'Clothing navigate gold',
  title: 'PCI virtual Chair',
  subTitle: 'target Realigned Handmade',
  status: 'Concrete Steel transform',
};

export const sampleWithFullData: ICategory = {
  id: 58360,
  parent: 'Oklahoma AI',
  title: 'parse',
  subTitle: 'indexing Kong Dynamic',
  imageUrl: 'Cotton',
  description: 'Music',
  country: 'Australia',
  code: 'Brand compressing Pizza',
  status: 'Agent monitor optimal',
};

export const sampleWithNewData: NewCategory = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
