import { ISlide, NewSlide } from './slide.model';

export const sampleWithRequiredData: ISlide = {
  id: 6436,
};

export const sampleWithPartialData: ISlide = {
  id: 40476,
  endDate: 'framework',
  tags: 'Personal azure sensor',
};

export const sampleWithFullData: ISlide = {
  id: 11894,
  title: 'protocol portals Plastic',
  subTitle: 'digital',
  status: 'even-keeled e-business user-centric',
  country: 'Guinea-Bissau',
  startDate: 'Computers',
  endDate: 'Identity magnetic',
  imageUrl: 'Checking',
  dealUrl: 'Marketing',
  tags: 'Norway background application',
};

export const sampleWithNewData: NewSlide = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
