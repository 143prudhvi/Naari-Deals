import { ISlide, NewSlide } from './slide.model';

export const sampleWithRequiredData: ISlide = {
  id: 6436,
};

export const sampleWithPartialData: ISlide = {
  id: 55451,
  startDate: 'Plastic e-enable',
};

export const sampleWithFullData: ISlide = {
  id: 94716,
  title: 'azure',
  subTitle: 'Bedfordshire Producer Lakes',
  imageUrl: 'Plastic Self-enabling Buckinghamshire',
  status: 'framework navigate',
  country: 'Luxembourg',
  startDate: '24/7 Account',
  endDate: 'encompassing HTTP',
  dealUrl: 'teal',
};

export const sampleWithNewData: NewSlide = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
