import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILoginProfile, NewLoginProfile } from '../login-profile.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILoginProfile for edit and NewLoginProfileFormGroupInput for create.
 */
type LoginProfileFormGroupInput = ILoginProfile | PartialWithRequiredKeyOf<NewLoginProfile>;

type LoginProfileFormDefaults = Pick<NewLoginProfile, 'id'>;

type LoginProfileFormGroupContent = {
  id: FormControl<ILoginProfile['id'] | NewLoginProfile['id']>;
  userName: FormControl<ILoginProfile['userName']>;
  userId: FormControl<ILoginProfile['userId']>;
  memberType: FormControl<ILoginProfile['memberType']>;
  memberId: FormControl<ILoginProfile['memberId']>;
  phoneNumber: FormControl<ILoginProfile['phoneNumber']>;
  emailId: FormControl<ILoginProfile['emailId']>;
  password: FormControl<ILoginProfile['password']>;
  activationStatus: FormControl<ILoginProfile['activationStatus']>;
  activationCode: FormControl<ILoginProfile['activationCode']>;
};

export type LoginProfileFormGroup = FormGroup<LoginProfileFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LoginProfileFormService {
  createLoginProfileFormGroup(loginProfile: LoginProfileFormGroupInput = { id: null }): LoginProfileFormGroup {
    const loginProfileRawValue = {
      ...this.getFormDefaults(),
      ...loginProfile,
    };
    return new FormGroup<LoginProfileFormGroupContent>({
      id: new FormControl(
        { value: loginProfileRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      userName: new FormControl(loginProfileRawValue.userName),
      userId: new FormControl(loginProfileRawValue.userId),
      memberType: new FormControl(loginProfileRawValue.memberType),
      memberId: new FormControl(loginProfileRawValue.memberId),
      phoneNumber: new FormControl(loginProfileRawValue.phoneNumber),
      emailId: new FormControl(loginProfileRawValue.emailId),
      password: new FormControl(loginProfileRawValue.password),
      activationStatus: new FormControl(loginProfileRawValue.activationStatus),
      activationCode: new FormControl(loginProfileRawValue.activationCode),
    });
  }

  getLoginProfile(form: LoginProfileFormGroup): ILoginProfile | NewLoginProfile {
    return form.getRawValue() as ILoginProfile | NewLoginProfile;
  }

  resetForm(form: LoginProfileFormGroup, loginProfile: LoginProfileFormGroupInput): void {
    const loginProfileRawValue = { ...this.getFormDefaults(), ...loginProfile };
    form.reset(
      {
        ...loginProfileRawValue,
        id: { value: loginProfileRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LoginProfileFormDefaults {
    return {
      id: null,
    };
  }
}
