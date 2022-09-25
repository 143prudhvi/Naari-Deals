export interface IDealType {
  id: number;
  dealType?: string | null;
  description?: string | null;
  imageUrl?: string | null;
}

export type NewDealType = Omit<IDealType, 'id'> & { id: null };
