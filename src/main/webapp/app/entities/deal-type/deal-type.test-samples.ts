import { IDealType, NewDealType } from './deal-type.model';

export const sampleWithRequiredData: IDealType = {
  id: 11532,
};

export const sampleWithPartialData: IDealType = {
  id: 81490,
  title: 'Fantastic Bedfordshire',
  subTitle: 'compress',
  country: 'Malaysia',
};

export const sampleWithFullData: IDealType = {
  id: 50327,
  title: 'customized system calculate',
  subTitle: 'driver monetize',
  icon: 'Botswana Lead',
  bgColor: 'Plastic Frozen SQL',
  country: 'Madagascar',
  code: 'Road',
  status: 'invoice users',
  display: true,
};

export const sampleWithNewData: NewDealType = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
