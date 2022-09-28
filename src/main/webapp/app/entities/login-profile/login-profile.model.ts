export interface ILoginProfile {
  id: number;
  userName?: string | null;
  userId?: string | null;
  memberType?: string | null;
  memberId?: string | null;
  phoneNumber?: string | null;
  emailId?: string | null;
  password?: string | null;
  activationStatus?: string | null;
  activationCode?: string | null;
}

export type NewLoginProfile = Omit<ILoginProfile, 'id'> & { id: null };
