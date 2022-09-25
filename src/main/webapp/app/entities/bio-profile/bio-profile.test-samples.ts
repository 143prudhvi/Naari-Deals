import { IBioProfile, NewBioProfile } from './bio-profile.model';

export const sampleWithRequiredData: IBioProfile = {
  id: 539,
};

export const sampleWithPartialData: IBioProfile = {
  id: 44719,
};

export const sampleWithFullData: IBioProfile = {
  id: 39034,
  userId: 'monetize Missouri',
  firstName: 'Durward',
  lastName: 'McLaughlin',
  dob: 'deposit',
  gender: 'methodologies',
  imageUrl: 'Focused',
};

export const sampleWithNewData: NewBioProfile = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
