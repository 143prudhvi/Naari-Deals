import { IMemberType, NewMemberType } from './member-type.model';

export const sampleWithRequiredData: IMemberType = {
  id: 88960,
};

export const sampleWithPartialData: IMemberType = {
  id: 22681,
  memberType: 'River Buckinghamshire Awesome',
};

export const sampleWithFullData: IMemberType = {
  id: 71530,
  memberType: 'Kenyan Rubber',
  description: 'productize',
  imageUrl: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewMemberType = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
