import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDealType, NewDealType } from '../deal-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDealType for edit and NewDealTypeFormGroupInput for create.
 */
type DealTypeFormGroupInput = IDealType | PartialWithRequiredKeyOf<NewDealType>;

type DealTypeFormDefaults = Pick<NewDealType, 'id'>;

type DealTypeFormGroupContent = {
  id: FormControl<IDealType['id'] | NewDealType['id']>;
  dealType: FormControl<IDealType['dealType']>;
  description: FormControl<IDealType['description']>;
  imageUrl: FormControl<IDealType['imageUrl']>;
};

export type DealTypeFormGroup = FormGroup<DealTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DealTypeFormService {
  createDealTypeFormGroup(dealType: DealTypeFormGroupInput = { id: null }): DealTypeFormGroup {
    const dealTypeRawValue = {
      ...this.getFormDefaults(),
      ...dealType,
    };
    return new FormGroup<DealTypeFormGroupContent>({
      id: new FormControl(
        { value: dealTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      dealType: new FormControl(dealTypeRawValue.dealType),
      description: new FormControl(dealTypeRawValue.description),
      imageUrl: new FormControl(dealTypeRawValue.imageUrl),
    });
  }

  getDealType(form: DealTypeFormGroup): IDealType | NewDealType {
    return form.getRawValue() as IDealType | NewDealType;
  }

  resetForm(form: DealTypeFormGroup, dealType: DealTypeFormGroupInput): void {
    const dealTypeRawValue = { ...this.getFormDefaults(), ...dealType };
    form.reset(
      {
        ...dealTypeRawValue,
        id: { value: dealTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DealTypeFormDefaults {
    return {
      id: null,
    };
  }
}
