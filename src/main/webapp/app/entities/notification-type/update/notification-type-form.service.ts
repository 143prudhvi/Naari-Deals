import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { INotificationType, NewNotificationType } from '../notification-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INotificationType for edit and NewNotificationTypeFormGroupInput for create.
 */
type NotificationTypeFormGroupInput = INotificationType | PartialWithRequiredKeyOf<NewNotificationType>;

type NotificationTypeFormDefaults = Pick<NewNotificationType, 'id'>;

type NotificationTypeFormGroupContent = {
  id: FormControl<INotificationType['id'] | NewNotificationType['id']>;
  type: FormControl<INotificationType['type']>;
  description: FormControl<INotificationType['description']>;
};

export type NotificationTypeFormGroup = FormGroup<NotificationTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NotificationTypeFormService {
  createNotificationTypeFormGroup(notificationType: NotificationTypeFormGroupInput = { id: null }): NotificationTypeFormGroup {
    const notificationTypeRawValue = {
      ...this.getFormDefaults(),
      ...notificationType,
    };
    return new FormGroup<NotificationTypeFormGroupContent>({
      id: new FormControl(
        { value: notificationTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      type: new FormControl(notificationTypeRawValue.type),
      description: new FormControl(notificationTypeRawValue.description),
    });
  }

  getNotificationType(form: NotificationTypeFormGroup): INotificationType | NewNotificationType {
    return form.getRawValue() as INotificationType | NewNotificationType;
  }

  resetForm(form: NotificationTypeFormGroup, notificationType: NotificationTypeFormGroupInput): void {
    const notificationTypeRawValue = { ...this.getFormDefaults(), ...notificationType };
    form.reset(
      {
        ...notificationTypeRawValue,
        id: { value: notificationTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NotificationTypeFormDefaults {
    return {
      id: null,
    };
  }
}
