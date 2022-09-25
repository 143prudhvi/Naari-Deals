import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEmailSubscription, NewEmailSubscription } from '../email-subscription.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmailSubscription for edit and NewEmailSubscriptionFormGroupInput for create.
 */
type EmailSubscriptionFormGroupInput = IEmailSubscription | PartialWithRequiredKeyOf<NewEmailSubscription>;

type EmailSubscriptionFormDefaults = Pick<NewEmailSubscription, 'id'>;

type EmailSubscriptionFormGroupContent = {
  id: FormControl<IEmailSubscription['id'] | NewEmailSubscription['id']>;
  email: FormControl<IEmailSubscription['email']>;
  country: FormControl<IEmailSubscription['country']>;
};

export type EmailSubscriptionFormGroup = FormGroup<EmailSubscriptionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmailSubscriptionFormService {
  createEmailSubscriptionFormGroup(emailSubscription: EmailSubscriptionFormGroupInput = { id: null }): EmailSubscriptionFormGroup {
    const emailSubscriptionRawValue = {
      ...this.getFormDefaults(),
      ...emailSubscription,
    };
    return new FormGroup<EmailSubscriptionFormGroupContent>({
      id: new FormControl(
        { value: emailSubscriptionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      email: new FormControl(emailSubscriptionRawValue.email),
      country: new FormControl(emailSubscriptionRawValue.country),
    });
  }

  getEmailSubscription(form: EmailSubscriptionFormGroup): IEmailSubscription | NewEmailSubscription {
    return form.getRawValue() as IEmailSubscription | NewEmailSubscription;
  }

  resetForm(form: EmailSubscriptionFormGroup, emailSubscription: EmailSubscriptionFormGroupInput): void {
    const emailSubscriptionRawValue = { ...this.getFormDefaults(), ...emailSubscription };
    form.reset(
      {
        ...emailSubscriptionRawValue,
        id: { value: emailSubscriptionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EmailSubscriptionFormDefaults {
    return {
      id: null,
    };
  }
}
