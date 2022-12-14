import { IDeal, NewDeal } from './deal.model';

export const sampleWithRequiredData: IDeal = {
  id: 51467,
};

export const sampleWithPartialData: IDeal = {
  id: 79443,
  description: '../fake-data/blob/hipster.txt',
  imageUrl: '../fake-data/blob/hipster.txt',
  dealUrl: '../fake-data/blob/hipster.txt',
  postedBy: 'Account Brand',
  postedDate: 'Locks Rubber bus',
  endDate: 'quantifying deposit Engineer',
  originalPrice: 'Forks Garden Buckinghamshire',
  currentPrice: 'silver',
  discount: 'wireless Afghani',
  discountType: 'Music Dram',
  approved: false,
  country: 'Anguilla',
  pinCode: 'Handcrafted',
  merchant: 'capacitor',
  tags: 'Cambridgeshire',
  brand: 'Implementation incentivize Home',
};

export const sampleWithFullData: IDeal = {
  id: 54755,
  title: 'bypassing Bedfordshire',
  description: '../fake-data/blob/hipster.txt',
  imageUrl: '../fake-data/blob/hipster.txt',
  dealUrl: '../fake-data/blob/hipster.txt',
  highlight: 'productivity',
  postedBy: 'reboot',
  postedDate: 'bypass Palau',
  startDate: 'Granite Causeway Ball',
  endDate: 'Checking hard',
  originalPrice: 'emulation',
  currentPrice: 'strategic',
  priceTag: 'Tuvalu',
  discount: 'Concrete invoice',
  discountType: 'Dakota',
  active: 'multimedia Dollar local',
  approved: true,
  country: 'Burkina Faso',
  city: 'East Kendallfurt',
  pinCode: 'fresh-thinking transmit',
  merchant: 'mission-critical Forward',
  category: 'Circle Arkansas',
  tags: 'revolutionary Unbranded web-readiness',
  brand: 'Practical',
  expired: false,
};

export const sampleWithNewData: NewDeal = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
