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
  imageUrl: FormControl<ISlide['imageUrl']>;
  status: FormControl<ISlide['status']>;
  country: FormControl<ISlide['country']>;
  startDate: FormControl<ISlide['startDate']>;
  endDate: FormControl<ISlide['endDate']>;
  dealUrl: FormControl<ISlide['dealUrl']>;
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
      imageUrl: new FormControl(slideRawValue.imageUrl),
      status: new FormControl(slideRawValue.status),
      country: new FormControl(slideRawValue.country),
      startDate: new FormControl(slideRawValue.startDate),
      endDate: new FormControl(slideRawValue.endDate),
      dealUrl: new FormControl(slideRawValue.dealUrl),
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
