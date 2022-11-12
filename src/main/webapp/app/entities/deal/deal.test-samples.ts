import { IDeal, NewDeal } from './deal.model';

export const sampleWithRequiredData: IDeal = {
  id: 51467,
};

export const sampleWithPartialData: IDeal = {
  id: 72350,
  description: '../fake-data/blob/hipster.txt',
  imageUrl: 'cross-platform',
  dealUrl: 'capacitor neural Ports',
  postedDate: 'Oklahoma Account',
  startDate: 'Profit-focused Industrial',
  originalPrice: 'RSS',
  currentPrice: 'Garden Buckinghamshire teal',
  discount: 'wireless',
  active: 'Afghani',
  approved: false,
  city: 'North Caseychester',
  pinCode: 'withdrawal',
  category: 'Handcrafted',
  tags: 'capacitor',
  expired: false,
};

export const sampleWithFullData: IDeal = {
  id: 51686,
  title: 'invoice red heuristic',
  description: '../fake-data/blob/hipster.txt',
  imageUrl: 'Awesome',
  dealUrl: 'Handmade yellow productivity',
  postedBy: 'reboot',
  postedDate: 'bypass Palau',
  startDate: 'Granite Causeway Ball',
  endDate: 'Checking hard',
  originalPrice: 'emulation',
  currentPrice: 'strategic',
  discount: 'Tuvalu',
  discountType: 'Concrete invoice',
  active: 'Dakota',
  approved: true,
  country: 'Eritrea',
  city: 'Ferrychester',
  pinCode: 'Future',
  merchant: 'Liaison salmon global',
  category: 'transmit Bedfordshire Unbranded',
  tags: 'Berkshire Borders Kitts',
  brand: 'Account',
  expired: false,
};

export const sampleWithNewData: NewDeal = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
