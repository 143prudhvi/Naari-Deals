export interface IBioProfile {
  id: number;
  userId?: string | null;
  firstName?: string | null;
  lastName?: string | null;
  dob?: string | null;
  gender?: string | null;
  imageUrl?: string | null;
}

export type NewBioProfile = Omit<IBioProfile, 'id'> & { id: null };
