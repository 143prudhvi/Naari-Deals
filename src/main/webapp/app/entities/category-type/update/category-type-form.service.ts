import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICategoryType, NewCategoryType } from '../category-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICategoryType for edit and NewCategoryTypeFormGroupInput for create.
 */
type CategoryTypeFormGroupInput = ICategoryType | PartialWithRequiredKeyOf<NewCategoryType>;

type CategoryTypeFormDefaults = Pick<NewCategoryType, 'id'>;

type CategoryTypeFormGroupContent = {
  id: FormControl<ICategoryType['id'] | NewCategoryType['id']>;
  title: FormControl<ICategoryType['title']>;
  subTitle: FormControl<ICategoryType['subTitle']>;
  icon: FormControl<ICategoryType['icon']>;
  bgColor: FormControl<ICategoryType['bgColor']>;
  country: FormControl<ICategoryType['country']>;
  code: FormControl<ICategoryType['code']>;
  status: FormControl<ICategoryType['status']>;
};

export type CategoryTypeFormGroup = FormGroup<CategoryTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CategoryTypeFormService {
  createCategoryTypeFormGroup(categoryType: CategoryTypeFormGroupInput = { id: null }): CategoryTypeFormGroup {
    const categoryTypeRawValue = {
      ...this.getFormDefaults(),
      ...categoryType,
    };
    return new FormGroup<CategoryTypeFormGroupContent>({
      id: new FormControl(
        { value: categoryTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      title: new FormControl(categoryTypeRawValue.title),
      subTitle: new FormControl(categoryTypeRawValue.subTitle),
      icon: new FormControl(categoryTypeRawValue.icon),
      bgColor: new FormControl(categoryTypeRawValue.bgColor),
      country: new FormControl(categoryTypeRawValue.country),
      code: new FormControl(categoryTypeRawValue.code),
      status: new FormControl(categoryTypeRawValue.status),
    });
  }

  getCategoryType(form: CategoryTypeFormGroup): ICategoryType | NewCategoryType {
    return form.getRawValue() as ICategoryType | NewCategoryType;
  }

  resetForm(form: CategoryTypeFormGroup, categoryType: CategoryTypeFormGroupInput): void {
    const categoryTypeRawValue = { ...this.getFormDefaults(), ...categoryType };
    form.reset(
      {
        ...categoryTypeRawValue,
        id: { value: categoryTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CategoryTypeFormDefaults {
    return {
      id: null,
    };
  }
}
