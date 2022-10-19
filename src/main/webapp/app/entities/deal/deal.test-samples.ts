import { IDeal, NewDeal } from './deal.model';

export const sampleWithRequiredData: IDeal = {
  id: 51467,
};

export const sampleWithPartialData: IDeal = {
  id: 65313,
  description: '../fake-data/blob/hipster.txt',
  imageUrl: '../fake-data/blob/hipster.txt',
  dealUrl: '../fake-data/blob/hipster.txt',
  postedDate: 'payment',
  startDate: 'benchmark capacitor neural',
  originalPrice: 'deposit Oklahoma',
  currentPrice: 'Sum Profit-focused',
  discount: 'Engineer',
  active: 'Forks Garden Buckinghamshire',
  approved: false,
  city: 'Port Chadrick',
  pinCode: 'wireless Afghani',
  tags: 'Music Dram',
};

export const sampleWithFullData: IDeal = {
  id: 46295,
  title: 'analyzer',
  description: '../fake-data/blob/hipster.txt',
  imageUrl: '../fake-data/blob/hipster.txt',
  dealUrl: '../fake-data/blob/hipster.txt',
  postedBy: 'Bahamian capacitor',
  postedDate: 'Cambridgeshire',
  startDate: 'Implementation incentivize Home',
  endDate: 'Buckinghamshire sensor',
  originalPrice: 'Incredible',
  currentPrice: 'Handmade',
  discount: 'Pennsylvania back-end',
  discountType: 'hack sensor',
  active: 'Ball Place Fish',
  approved: false,
  country: 'Cameroon',
  city: 'East Emily',
  pinCode: 'strategic',
  merchant: 'Tuvalu',
  tags: 'Concrete invoice',
};

export const sampleWithNewData: NewDeal = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
