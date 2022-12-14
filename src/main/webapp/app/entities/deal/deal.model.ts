export interface IDeal {
  id: number;
  title?: string | null;
  description?: string | null;
  imageUrl?: string | null;
  dealUrl?: string | null;
  highlight?: string | null;
  postedBy?: string | null;
  postedDate?: string | null;
  startDate?: string | null;
  endDate?: string | null;
  originalPrice?: string | null;
  currentPrice?: string | null;
  priceTag?: string | null;
  discount?: string | null;
  discountType?: string | null;
  active?: string | null;
  approved?: boolean | null;
  country?: string | null;
  city?: string | null;
  pinCode?: string | null;
  merchant?: string | null;
  category?: string | null;
  tags?: string | null;
  brand?: string | null;
  expired?: boolean | null;
}

export type NewDeal = Omit<IDeal, 'id'> & { id: null };
