import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMerchant, NewMerchant } from '../merchant.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMerchant for edit and NewMerchantFormGroupInput for create.
 */
type MerchantFormGroupInput = IMerchant | PartialWithRequiredKeyOf<NewMerchant>;

type MerchantFormDefaults = Pick<NewMerchant, 'id'>;

type MerchantFormGroupContent = {
  id: FormControl<IMerchant['id'] | NewMerchant['id']>;
  name: FormControl<IMerchant['name']>;
  country: FormControl<IMerchant['country']>;
  city: FormControl<IMerchant['city']>;
  storeIcon: FormControl<IMerchant['storeIcon']>;
  type: FormControl<IMerchant['type']>;
  location: FormControl<IMerchant['location']>;
  siteUrl: FormControl<IMerchant['siteUrl']>;
};

export type MerchantFormGroup = FormGroup<MerchantFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MerchantFormService {
  createMerchantFormGroup(merchant: MerchantFormGroupInput = { id: null }): MerchantFormGroup {
    const merchantRawValue = {
      ...this.getFormDefaults(),
      ...merchant,
    };
    return new FormGroup<MerchantFormGroupContent>({
      id: new FormControl(
        { value: merchantRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(merchantRawValue.name),
      country: new FormControl(merchantRawValue.country),
      city: new FormControl(merchantRawValue.city),
      storeIcon: new FormControl(merchantRawValue.storeIcon),
      type: new FormControl(merchantRawValue.type),
      location: new FormControl(merchantRawValue.location),
      siteUrl: new FormControl(merchantRawValue.siteUrl),
    });
  }

  getMerchant(form: MerchantFormGroup): IMerchant | NewMerchant {
    return form.getRawValue() as IMerchant | NewMerchant;
  }

  resetForm(form: MerchantFormGroup, merchant: MerchantFormGroupInput): void {
    const merchantRawValue = { ...this.getFormDefaults(), ...merchant };
    form.reset(
      {
        ...merchantRawValue,
        id: { value: merchantRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MerchantFormDefaults {
    return {
      id: null,
    };
  }
}
