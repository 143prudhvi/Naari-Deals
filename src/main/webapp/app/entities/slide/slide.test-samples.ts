import { ISlide, NewSlide } from './slide.model';

export const sampleWithRequiredData: ISlide = {
  id: 6436,
};

export const sampleWithPartialData: ISlide = {
  id: 21312,
  startDate: 'enable disintermediate',
};

export const sampleWithFullData: ISlide = {
  id: 59641,
  title: 'Diverse Assurance yellow',
  subTitle: 'Pants Plastic Self-enabling',
  imageUrl: 'port framework',
  status: 'user-centric B2C Pre-emptive',
  country: 'Ukraine',
  startDate: 'encompassing HTTP',
  endDate: 'teal',
};

export const sampleWithNewData: NewSlide = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
