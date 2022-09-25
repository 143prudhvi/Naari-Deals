import { ICategory, NewCategory } from './category.model';

export const sampleWithRequiredData: ICategory = {
  id: 2529,
};

export const sampleWithPartialData: ICategory = {
  id: 14860,
  name: 'emulation',
  icon: '../fake-data/blob/hipster.txt',
  description: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: ICategory = {
  id: 81675,
  name: 'Clothing navigate gold',
  icon: '../fake-data/blob/hipster.txt',
  description: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewCategory = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
