import { ILoginProfile, NewLoginProfile } from './login-profile.model';

export const sampleWithRequiredData: ILoginProfile = {
  id: 16348,
};

export const sampleWithPartialData: ILoginProfile = {
  id: 12793,
  userName: 'Strategist Myanmar',
  phoneNumber: 'Shoes Home technologies',
  emailId: 'Borders SDR',
  password: 'Berkshire Algeria reintermediate',
  activationStatus: 'auxiliary Savings',
};

export const sampleWithFullData: ILoginProfile = {
  id: 4568,
  userName: 'orchid Enhanced',
  userId: 'Pants',
  memberType: 'deliver extranet',
  memberId: '1080p',
  phoneNumber: 'Roads Malaysia',
  emailId: 'initiatives Fundamental',
  password: 'Passage sensor',
  activationStatus: 'Avon Keys primary',
  activationCode: 'PCI',
};

export const sampleWithNewData: NewLoginProfile = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
