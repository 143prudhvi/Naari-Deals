export interface IMerchant {
  id: number;
  code?: string | null;
  title?: string | null;
  subTitle?: string | null;
  address?: string | null;
  phone?: string | null;
  country?: string | null;
  city?: string | null;
  imageUrl?: string | null;
  type?: string | null;
  location?: string | null;
  siteUrl?: string | null;
  status?: string | null;
}

export type NewMerchant = Omit<IMerchant, 'id'> & { id: null };
