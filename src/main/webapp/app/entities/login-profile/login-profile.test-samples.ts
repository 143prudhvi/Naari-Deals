import { ILoginProfile, NewLoginProfile } from './login-profile.model';

export const sampleWithRequiredData: ILoginProfile = {
  id: 16348,
};

export const sampleWithPartialData: ILoginProfile = {
  id: 45733,
  userName: 'Way',
  emailId: 'Myanmar hack',
  password: 'Home',
  activationStatus: 'users Borders',
  activationCode: 'Division Berkshire Algeria',
};

export const sampleWithFullData: ILoginProfile = {
  id: 38868,
  userName: 'Web Games',
  userId: 'Small',
  memberType: 'withdrawal',
  phoneNumber: 'Manager',
  emailId: 'generating Liechtenstein',
  password: 'platforms',
  activationStatus: 'Missouri card Kyrgyz',
  activationCode: 'Cheese sexy',
};

export const sampleWithNewData: NewLoginProfile = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
