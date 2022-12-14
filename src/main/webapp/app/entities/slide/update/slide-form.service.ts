import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISlide, NewSlide } from '../slide.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISlide for edit and NewSlideFormGroupInput for create.
 */
type SlideFormGroupInput = ISlide | PartialWithRequiredKeyOf<NewSlide>;

type SlideFormDefaults = Pick<NewSlide, 'id'>;

type SlideFormGroupContent = {
  id: FormControl<ISlide['id'] | NewSlide['id']>;
  title: FormControl<ISlide['title']>;
  subTitle: FormControl<ISlide['subTitle']>;
  status: FormControl<ISlide['status']>;
  country: FormControl<ISlide['country']>;
  startDate: FormControl<ISlide['startDate']>;
  endDate: FormControl<ISlide['endDate']>;
  imageUrl: FormControl<ISlide['imageUrl']>;
  merchantIcon: FormControl<ISlide['merchantIcon']>;
  dealUrl: FormControl<ISlide['dealUrl']>;
  tags: FormControl<ISlide['tags']>;
};

export type SlideFormGroup = FormGroup<SlideFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SlideFormService {
  createSlideFormGroup(slide: SlideFormGroupInput = { id: null }): SlideFormGroup {
    const slideRawValue = {
      ...this.getFormDefaults(),
      ...slide,
    };
    return new FormGroup<SlideFormGroupContent>({
      id: new FormControl(
        { value: slideRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      title: new FormControl(slideRawValue.title),
      subTitle: new FormControl(slideRawValue.subTitle),
      status: new FormControl(slideRawValue.status),
      country: new FormControl(slideRawValue.country),
      startDate: new FormControl(slideRawValue.startDate),
      endDate: new FormControl(slideRawValue.endDate),
      imageUrl: new FormControl(slideRawValue.imageUrl),
      merchantIcon: new FormControl(slideRawValue.merchantIcon),
      dealUrl: new FormControl(slideRawValue.dealUrl),
      tags: new FormControl(slideRawValue.tags),
    });
  }

  getSlide(form: SlideFormGroup): ISlide | NewSlide {
    return form.getRawValue() as ISlide | NewSlide;
  }

  resetForm(form: SlideFormGroup, slide: SlideFormGroupInput): void {
    const slideRawValue = { ...this.getFormDefaults(), ...slide };
    form.reset(
      {
        ...slideRawValue,
        id: { value: slideRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SlideFormDefaults {
    return {
      id: null,
    };
  }
}
