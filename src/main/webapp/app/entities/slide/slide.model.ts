export interface ISlide {
  id: number;
  title?: string | null;
  subTitle?: string | null;
  imageUrl?: string | null;
  status?: string | null;
  country?: string | null;
  startDate?: string | null;
  endDate?: string | null;
}

export type NewSlide = Omit<ISlide, 'id'> & { id: null };
