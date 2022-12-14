export interface IFeedback {
  id: number;
  type?: string | null;
  name?: string | null;
  email?: string | null;
  phone?: string | null;
  userId?: string | null;
  message?: string | null;
}

export type NewFeedback = Omit<IFeedback, 'id'> & { id: null };
