export interface IEmailSubscription {
  id: number;
  email?: string | null;
  country?: string | null;
}

export type NewEmailSubscription = Omit<IEmailSubscription, 'id'> & { id: null };
