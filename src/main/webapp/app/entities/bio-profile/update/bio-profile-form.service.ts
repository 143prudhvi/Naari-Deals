import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IBioProfile, NewBioProfile } from '../bio-profile.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBioProfile for edit and NewBioProfileFormGroupInput for create.
 */
type BioProfileFormGroupInput = IBioProfile | PartialWithRequiredKeyOf<NewBioProfile>;

type BioProfileFormDefaults = Pick<NewBioProfile, 'id'>;

type BioProfileFormGroupContent = {
  id: FormControl<IBioProfile['id'] | NewBioProfile['id']>;
  userId: FormControl<IBioProfile['userId']>;
  firstName: FormControl<IBioProfile['firstName']>;
  lastName: FormControl<IBioProfile['lastName']>;
  dob: FormControl<IBioProfile['dob']>;
  gender: FormControl<IBioProfile['gender']>;
  imageUrl: FormControl<IBioProfile['imageUrl']>;
};

export type BioProfileFormGroup = FormGroup<BioProfileFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BioProfileFormService {
  createBioProfileFormGroup(bioProfile: BioProfileFormGroupInput = { id: null }): BioProfileFormGroup {
    const bioProfileRawValue = {
      ...this.getFormDefaults(),
      ...bioProfile,
    };
    return new FormGroup<BioProfileFormGroupContent>({
      id: new FormControl(
        { value: bioProfileRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      userId: new FormControl(bioProfileRawValue.userId),
      firstName: new FormControl(bioProfileRawValue.firstName),
      lastName: new FormControl(bioProfileRawValue.lastName),
      dob: new FormControl(bioProfileRawValue.dob),
      gender: new FormControl(bioProfileRawValue.gender),
      imageUrl: new FormControl(bioProfileRawValue.imageUrl),
    });
  }

  getBioProfile(form: BioProfileFormGroup): IBioProfile | NewBioProfile {
    return form.getRawValue() as IBioProfile | NewBioProfile;
  }

  resetForm(form: BioProfileFormGroup, bioProfile: BioProfileFormGroupInput): void {
    const bioProfileRawValue = { ...this.getFormDefaults(), ...bioProfile };
    form.reset(
      {
        ...bioProfileRawValue,
        id: { value: bioProfileRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): BioProfileFormDefaults {
    return {
      id: null,
    };
  }
}
