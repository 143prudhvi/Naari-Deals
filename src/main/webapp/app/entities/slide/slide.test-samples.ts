import { ISlide, NewSlide } from './slide.model';

export const sampleWithRequiredData: ISlide = {
  id: 6436,
};

export const sampleWithPartialData: ISlide = {
  id: 55451,
  endDate: 'Plastic e-enable',
};

export const sampleWithFullData: ISlide = {
  id: 94716,
  title: 'azure',
  subTitle: 'Bedfordshire Producer Lakes',
  status: 'Plastic Self-enabling Buckinghamshire',
  country: 'Guatemala',
  startDate: 'e-business',
  endDate: 'XML 24/7',
  imageUrl: 'Viaduct encompassing',
  dealUrl: 'Generic Marketing Operations',
};

export const sampleWithNewData: NewSlide = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
