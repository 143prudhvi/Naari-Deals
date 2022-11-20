import { ISlide, NewSlide } from './slide.model';

export const sampleWithRequiredData: ISlide = {
  id: 6436,
};

export const sampleWithPartialData: ISlide = {
  id: 14679,
  endDate: 'disintermediate',
  dealUrl: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: ISlide = {
  id: 59641,
  title: 'Diverse Assurance yellow',
  subTitle: 'Pants Plastic Self-enabling',
  status: 'port framework',
  country: 'San Marino',
  startDate: 'XML 24/7',
  endDate: 'Viaduct encompassing',
  imageUrl: 'Generic Marketing Operations',
  merchantIcon: 'background application',
  dealUrl: '../fake-data/blob/hipster.txt',
  tags: 'hacking relationships',
};

export const sampleWithNewData: NewSlide = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
