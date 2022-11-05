import { IDeal, NewDeal } from './deal.model';

export const sampleWithRequiredData: IDeal = {
  id: 51467,
};

export const sampleWithPartialData: IDeal = {
  id: 64179,
  description: '../fake-data/blob/hipster.txt',
  imageUrl: '../fake-data/blob/hipster.txt',
  dealUrl: '../fake-data/blob/hipster.txt',
  postedDate: 'Jewelery Account Brand',
  startDate: 'Locks Rubber bus',
  originalPrice: 'quantifying deposit Engineer',
  currentPrice: 'Forks Garden Buckinghamshire',
  discount: 'silver',
  active: 'wireless Afghani',
  approved: false,
  city: 'North Caseychester',
  pinCode: 'withdrawal',
  tags: 'Handcrafted',
  brand: 'capacitor',
};

export const sampleWithFullData: IDeal = {
  id: 6047,
  title: 'SMTP Implementation',
  description: '../fake-data/blob/hipster.txt',
  imageUrl: '../fake-data/blob/hipster.txt',
  dealUrl: '../fake-data/blob/hipster.txt',
  postedBy: 'heuristic intelligence',
  postedDate: 'bypassing Bedfordshire',
  startDate: 'productivity',
  endDate: 'reboot',
  originalPrice: 'bypass Palau',
  currentPrice: 'Granite Causeway Ball',
  discount: 'Checking hard',
  discountType: 'emulation',
  active: 'strategic',
  approved: false,
  country: 'Macao',
  city: 'Revere',
  pinCode: 'Concrete invoice',
  merchant: 'Dakota',
  tags: 'multimedia Dollar local',
  brand: 'Concrete New fresh-thinking',
  expired: true,
};

export const sampleWithNewData: NewDeal = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
