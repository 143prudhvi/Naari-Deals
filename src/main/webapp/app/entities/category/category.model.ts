export interface ICategory {
  id: number;
  title?: string | null;
  subTitle?: string | null;
  imageUrl?: string | null;
  description?: string | null;
  status?: string | null;
  country?: string | null;
  code?: string | null;
}

export type NewCategory = Omit<ICategory, 'id'> & { id: null };
