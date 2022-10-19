export interface ICategoryType {
  id: number;
  title?: string | null;
  subTitle?: string | null;
  icon?: string | null;
  bgColor?: string | null;
  country?: string | null;
  code?: string | null;
  status?: string | null;
}

export type NewCategoryType = Omit<ICategoryType, 'id'> & { id: null };
