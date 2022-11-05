export interface IBrand {
  id: number;
  title?: string | null;
  subTitle?: string | null;
  code?: string | null;
  status?: string | null;
  country?: string | null;
  imageUrl?: string | null;
}

export type NewBrand = Omit<IBrand, 'id'> & { id: null };
