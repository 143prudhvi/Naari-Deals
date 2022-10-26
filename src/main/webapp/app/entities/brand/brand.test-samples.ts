import { IBrand, NewBrand } from './brand.model';

export const sampleWithRequiredData: IBrand = {
  id: 69581,
};

export const sampleWithPartialData: IBrand = {
  id: 35902,
  title: 'experiences Forint Coordinator',
  subTitle: 'Pataca Metal Sol',
  status: 'bandwidth-monitored primary',
  country: 'Zambia',
  imageUrl: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IBrand = {
  id: 16212,
  title: 'Security Islands Money',
  subTitle: 'Canyon orchestrate',
  status: 'Parks invoice Incredible',
  country: 'Holy See (Vatican City State)',
  imageUrl: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewBrand = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
