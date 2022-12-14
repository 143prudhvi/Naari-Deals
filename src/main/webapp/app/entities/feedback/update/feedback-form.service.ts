import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFeedback, NewFeedback } from '../feedback.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFeedback for edit and NewFeedbackFormGroupInput for create.
 */
type FeedbackFormGroupInput = IFeedback | PartialWithRequiredKeyOf<NewFeedback>;

type FeedbackFormDefaults = Pick<NewFeedback, 'id'>;

type FeedbackFormGroupContent = {
  id: FormControl<IFeedback['id'] | NewFeedback['id']>;
  type: FormControl<IFeedback['type']>;
  name: FormControl<IFeedback['name']>;
  email: FormControl<IFeedback['email']>;
  phone: FormControl<IFeedback['phone']>;
  userId: FormControl<IFeedback['userId']>;
  message: FormControl<IFeedback['message']>;
};

export type FeedbackFormGroup = FormGroup<FeedbackFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FeedbackFormService {
  createFeedbackFormGroup(feedback: FeedbackFormGroupInput = { id: null }): FeedbackFormGroup {
    const feedbackRawValue = {
      ...this.getFormDefaults(),
      ...feedback,
    };
    return new FormGroup<FeedbackFormGroupContent>({
      id: new FormControl(
        { value: feedbackRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      type: new FormControl(feedbackRawValue.type),
      name: new FormControl(feedbackRawValue.name),
      email: new FormControl(feedbackRawValue.email),
      phone: new FormControl(feedbackRawValue.phone),
      userId: new FormControl(feedbackRawValue.userId),
      message: new FormControl(feedbackRawValue.message),
    });
  }

  getFeedback(form: FeedbackFormGroup): IFeedback | NewFeedback {
    return form.getRawValue() as IFeedback | NewFeedback;
  }

  resetForm(form: FeedbackFormGroup, feedback: FeedbackFormGroupInput): void {
    const feedbackRawValue = { ...this.getFormDefaults(), ...feedback };
    form.reset(
      {
        ...feedbackRawValue,
        id: { value: feedbackRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FeedbackFormDefaults {
    return {
      id: null,
    };
  }
}
