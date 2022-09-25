export interface INotificationType {
  id: number;
  type?: string | null;
  description?: string | null;
}

export type NewNotificationType = Omit<INotificationType, 'id'> & { id: null };
