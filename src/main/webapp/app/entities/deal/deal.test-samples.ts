import { IDeal, NewDeal } from './deal.model';

export const sampleWithRequiredData: IDeal = {
  id: 51467,
};

export const sampleWithPartialData: IDeal = {
  id: 28689,
  title: 'driver benchmark',
  description: '../fake-data/blob/hipster.txt',
  imageUrl: '../fake-data/blob/hipster.txt',
  postedBy: 'Brand indexing deposit',
  postedDate: 'bus Sum',
  endDate: 'deposit',
  originalPrice: 'Compatible Forks Garden',
  currentPrice: 'North silver',
  discountType: 'wireless Afghani',
  active: 'Music Dram',
  country: 'Kiribati',
  city: 'West Amarafurt',
  merchant: 'Bahamian capacitor',
  category: 'Cambridgeshire',
};

export const sampleWithFullData: IDeal = {
  id: 88827,
  type: 'red heuristic intelligence',
  title: 'bypassing Bedfordshire',
  description: '../fake-data/blob/hipster.txt',
  imageUrl: '../fake-data/blob/hipster.txt',
  dealUrl: '../fake-data/blob/hipster.txt',
  postedBy: 'productivity',
  postedDate: 'reboot',
  startDate: 'bypass Palau',
  endDate: 'Granite Causeway Ball',
  originalPrice: 'Checking hard',
  currentPrice: 'emulation',
  discount: 'strategic',
  discountType: 'Tuvalu',
  active: 'Concrete invoice',
  approved: false,
  country: 'Mexico',
  city: 'Murphyhaven',
  pinCode: 'deposit',
  merchant: 'Future',
  category: 'Liaison salmon global',
};

export const sampleWithNewData: NewDeal = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
