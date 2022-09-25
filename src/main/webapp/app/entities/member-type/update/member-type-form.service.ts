import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMemberType, NewMemberType } from '../member-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMemberType for edit and NewMemberTypeFormGroupInput for create.
 */
type MemberTypeFormGroupInput = IMemberType | PartialWithRequiredKeyOf<NewMemberType>;

type MemberTypeFormDefaults = Pick<NewMemberType, 'id'>;

type MemberTypeFormGroupContent = {
  id: FormControl<IMemberType['id'] | NewMemberType['id']>;
  memberType: FormControl<IMemberType['memberType']>;
  description: FormControl<IMemberType['description']>;
  imageUrl: FormControl<IMemberType['imageUrl']>;
};

export type MemberTypeFormGroup = FormGroup<MemberTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MemberTypeFormService {
  createMemberTypeFormGroup(memberType: MemberTypeFormGroupInput = { id: null }): MemberTypeFormGroup {
    const memberTypeRawValue = {
      ...this.getFormDefaults(),
      ...memberType,
    };
    return new FormGroup<MemberTypeFormGroupContent>({
      id: new FormControl(
        { value: memberTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      memberType: new FormControl(memberTypeRawValue.memberType),
      description: new FormControl(memberTypeRawValue.description),
      imageUrl: new FormControl(memberTypeRawValue.imageUrl),
    });
  }

  getMemberType(form: MemberTypeFormGroup): IMemberType | NewMemberType {
    return form.getRawValue() as IMemberType | NewMemberType;
  }

  resetForm(form: MemberTypeFormGroup, memberType: MemberTypeFormGroupInput): void {
    const memberTypeRawValue = { ...this.getFormDefaults(), ...memberType };
    form.reset(
      {
        ...memberTypeRawValue,
        id: { value: memberTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MemberTypeFormDefaults {
    return {
      id: null,
    };
  }
}
