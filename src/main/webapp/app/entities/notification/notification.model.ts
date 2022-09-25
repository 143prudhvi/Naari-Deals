export interface INotification {
  id: number;
  userId?: string | null;
  title?: string | null;
  message?: string | null;
  status?: string | null;
  type?: string | null;
  dateOfRead?: string | null;
  imageUrl?: string | null;
  originalPrice?: string | null;
  currentPrice?: string | null;
  discount?: string | null;
  discountType?: string | null;
}

export type NewNotification = Omit<INotification, 'id'> & { id: null };
