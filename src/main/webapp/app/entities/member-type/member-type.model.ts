export interface IMemberType {
  id: number;
  memberType?: string | null;
  description?: string | null;
  imageUrl?: string | null;
}

export type NewMemberType = Omit<IMemberType, 'id'> & { id: null };
