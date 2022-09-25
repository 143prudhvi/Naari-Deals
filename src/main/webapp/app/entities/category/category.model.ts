export interface ICategory {
  id: number;
  name?: string | null;
  icon?: string | null;
  description?: string | null;
}

export type NewCategory = Omit<ICategory, 'id'> & { id: null };
