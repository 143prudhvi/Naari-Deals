import { ICategory, NewCategory } from './category.model';

export const sampleWithRequiredData: ICategory = {
  id: 2529,
};

export const sampleWithPartialData: ICategory = {
  id: 91930,
  title: 'up USB Down-sized',
  subTitle: 'Facilitator synthesizing Directives',
  imageUrl: 'navigating',
};

export const sampleWithFullData: ICategory = {
  id: 54514,
  title: 'bus',
  subTitle: 'Accounts Computer Handmade',
  imageUrl: 'generating',
  description: '../fake-data/blob/hipster.txt',
  status: 'pixel syndicate',
  country: 'Samoa',
  code: 'metrics Shoals hub',
};

export const sampleWithNewData: NewCategory = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
