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
  title: FormControl<IDealType['title']>;
  subTitle: FormControl<IDealType['subTitle']>;
  icon: FormControl<IDealType['icon']>;
  bgColor: FormControl<IDealType['bgColor']>;
  country: FormControl<IDealType['country']>;
  code: FormControl<IDealType['code']>;
  status: FormControl<IDealType['status']>;
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
      title: new FormControl(dealTypeRawValue.title),
      subTitle: new FormControl(dealTypeRawValue.subTitle),
      icon: new FormControl(dealTypeRawValue.icon),
      bgColor: new FormControl(dealTypeRawValue.bgColor),
      country: new FormControl(dealTypeRawValue.country),
      code: new FormControl(dealTypeRawValue.code),
      status: new FormControl(dealTypeRawValue.status),
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
