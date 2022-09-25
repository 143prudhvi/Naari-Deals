export interface IMerchant {
  id: number;
  name?: string | null;
  country?: string | null;
  city?: string | null;
  storeIcon?: string | null;
  type?: string | null;
  location?: string | null;
}

export type NewMerchant = Omit<IMerchant, 'id'> & { id: null };
