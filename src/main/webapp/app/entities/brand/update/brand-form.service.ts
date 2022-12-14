import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IBrand, NewBrand } from '../brand.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBrand for edit and NewBrandFormGroupInput for create.
 */
type BrandFormGroupInput = IBrand | PartialWithRequiredKeyOf<NewBrand>;

type BrandFormDefaults = Pick<NewBrand, 'id'>;

type BrandFormGroupContent = {
  id: FormControl<IBrand['id'] | NewBrand['id']>;
  title: FormControl<IBrand['title']>;
  subTitle: FormControl<IBrand['subTitle']>;
  code: FormControl<IBrand['code']>;
  status: FormControl<IBrand['status']>;
  country: FormControl<IBrand['country']>;
  imageUrl: FormControl<IBrand['imageUrl']>;
  siteUrl: FormControl<IBrand['siteUrl']>;
};

export type BrandFormGroup = FormGroup<BrandFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BrandFormService {
  createBrandFormGroup(brand: BrandFormGroupInput = { id: null }): BrandFormGroup {
    const brandRawValue = {
      ...this.getFormDefaults(),
      ...brand,
    };
    return new FormGroup<BrandFormGroupContent>({
      id: new FormControl(
        { value: brandRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      title: new FormControl(brandRawValue.title),
      subTitle: new FormControl(brandRawValue.subTitle),
      code: new FormControl(brandRawValue.code),
      status: new FormControl(brandRawValue.status),
      country: new FormControl(brandRawValue.country),
      imageUrl: new FormControl(brandRawValue.imageUrl),
      siteUrl: new FormControl(brandRawValue.siteUrl),
    });
  }

  getBrand(form: BrandFormGroup): IBrand | NewBrand {
    return form.getRawValue() as IBrand | NewBrand;
  }

  resetForm(form: BrandFormGroup, brand: BrandFormGroupInput): void {
    const brandRawValue = { ...this.getFormDefaults(), ...brand };
    form.reset(
      {
        ...brandRawValue,
        id: { value: brandRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): BrandFormDefaults {
    return {
      id: null,
    };
  }
}
