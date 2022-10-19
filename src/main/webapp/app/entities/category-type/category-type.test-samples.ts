import { ICategoryType, NewCategoryType } from './category-type.model';

export const sampleWithRequiredData: ICategoryType = {
  id: 85503,
};

export const sampleWithPartialData: ICategoryType = {
  id: 49167,
  title: 'Drive bypassing orange',
  subTitle: 'Rubber bandwidth-monitored Forward',
  icon: 'violet ubiquitous',
  bgColor: 'deposit rich Savings',
  code: 'Strategist Generic',
  status: 'Wooden indexing',
};

export const sampleWithFullData: ICategoryType = {
  id: 10030,
  title: 'seize circuit Outdoors',
  subTitle: 'Gorgeous connecting invoice',
  icon: 'up',
  bgColor: 'scalable',
  country: 'Equatorial Guinea',
  code: 'Soft',
  status: 'IB mission-critical',
};

export const sampleWithNewData: NewCategoryType = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
