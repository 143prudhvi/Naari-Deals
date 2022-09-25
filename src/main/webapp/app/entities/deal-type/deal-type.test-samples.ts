import { IDealType, NewDealType } from './deal-type.model';

export const sampleWithRequiredData: IDealType = {
  id: 11532,
};

export const sampleWithPartialData: IDealType = {
  id: 11244,
  dealType: 'Bedfordshire generating',
  description: 'Meadows orchestrate',
};

export const sampleWithFullData: IDealType = {
  id: 26932,
  dealType: 'Jamahiriya customized',
  description: 'action-items payment driver',
  imageUrl: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewDealType = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
