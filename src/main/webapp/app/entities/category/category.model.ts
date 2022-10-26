export interface ICategory {
  id: number;
  parent?: string | null;
  title?: string | null;
  subTitle?: string | null;
  imageUrl?: string | null;
  description?: string | null;
  country?: string | null;
  code?: string | null;
  status?: string | null;
}

export type NewCategory = Omit<ICategory, 'id'> & { id: null };
