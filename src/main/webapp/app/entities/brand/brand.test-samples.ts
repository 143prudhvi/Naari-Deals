import { IBrand, NewBrand } from './brand.model';

export const sampleWithRequiredData: IBrand = {
  id: 69581,
};

export const sampleWithPartialData: IBrand = {
  id: 42262,
  title: 'synthesize mobile Pizza',
  subTitle: 'bypassing Baby',
  code: 'empower regional parse',
  status: 'Security Islands Money',
  country: 'Monaco',
  siteUrl: 'Licensed sensor',
};

export const sampleWithFullData: IBrand = {
  id: 86547,
  title: 'Investor collaboration',
  subTitle: 'methodical Cambridgeshire',
  code: 'Applications',
  status: 'calculate calculating',
  country: 'Gabon',
  imageUrl: 'online Chicken Oklahoma',
  siteUrl: 'Steel Frozen',
};

export const sampleWithNewData: NewBrand = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
