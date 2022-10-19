export interface IDealType {
  id: number;
  title?: string | null;
  subTitle?: string | null;
  icon?: string | null;
  bgColor?: string | null;
  country?: string | null;
  code?: string | null;
  status?: string | null;
}

export type NewDealType = Omit<IDealType, 'id'> & { id: null };
