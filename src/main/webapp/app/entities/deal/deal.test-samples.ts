import { IDeal, NewDeal } from './deal.model';

export const sampleWithRequiredData: IDeal = {
  id: 51467,
};

export const sampleWithPartialData: IDeal = {
  id: 72350,
  description: '../fake-data/blob/hipster.txt',
  imageUrl: '../fake-data/blob/hipster.txt',
  dealUrl: '../fake-data/blob/hipster.txt',
  postedDate: 'cross-platform',
  startDate: 'capacitor neural Ports',
  originalPrice: 'Oklahoma Account',
  currentPrice: 'Profit-focused Industrial',
  discount: 'RSS',
  active: 'Garden Buckinghamshire teal',
  approved: false,
  city: 'Lake Marvin',
  pinCode: 'Afghani',
  category: 'Music Dram',
  tags: 'Home payment',
  expired: false,
};

export const sampleWithFullData: IDeal = {
  id: 16013,
  title: 'Human Cambridgeshire XSS',
  description: '../fake-data/blob/hipster.txt',
  imageUrl: '../fake-data/blob/hipster.txt',
  dealUrl: '../fake-data/blob/hipster.txt',
  postedBy: 'incentivize',
  postedDate: 'intelligence',
  startDate: 'bypassing Bedfordshire',
  endDate: 'productivity',
  originalPrice: 'reboot',
  currentPrice: 'bypass Palau',
  discount: 'Granite Causeway Ball',
  discountType: 'Checking hard',
  active: 'emulation',
  approved: false,
  country: 'Guinea-Bissau',
  city: 'Lake Darbyport',
  pinCode: 'index open-source Norway',
  merchant: 'Organized Personal',
  category: 'Public-key Future',
  tags: 'Liaison salmon global',
  brand: 'transmit Bedfordshire Unbranded',
  expired: true,
};

export const sampleWithNewData: NewDeal = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
