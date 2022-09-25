import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { INotification, NewNotification } from '../notification.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INotification for edit and NewNotificationFormGroupInput for create.
 */
type NotificationFormGroupInput = INotification | PartialWithRequiredKeyOf<NewNotification>;

type NotificationFormDefaults = Pick<NewNotification, 'id'>;

type NotificationFormGroupContent = {
  id: FormControl<INotification['id'] | NewNotification['id']>;
  userId: FormControl<INotification['userId']>;
  title: FormControl<INotification['title']>;
  message: FormControl<INotification['message']>;
  status: FormControl<INotification['status']>;
  type: FormControl<INotification['type']>;
  dateOfRead: FormControl<INotification['dateOfRead']>;
  imageUrl: FormControl<INotification['imageUrl']>;
  originalPrice: FormControl<INotification['originalPrice']>;
  currentPrice: FormControl<INotification['currentPrice']>;
  discount: FormControl<INotification['discount']>;
  discountType: FormControl<INotification['discountType']>;
};

export type NotificationFormGroup = FormGroup<NotificationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NotificationFormService {
  createNotificationFormGroup(notification: NotificationFormGroupInput = { id: null }): NotificationFormGroup {
    const notificationRawValue = {
      ...this.getFormDefaults(),
      ...notification,
    };
    return new FormGroup<NotificationFormGroupContent>({
      id: new FormControl(
        { value: notificationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      userId: new FormControl(notificationRawValue.userId),
      title: new FormControl(notificationRawValue.title),
      message: new FormControl(notificationRawValue.message),
      status: new FormControl(notificationRawValue.status),
      type: new FormControl(notificationRawValue.type),
      dateOfRead: new FormControl(notificationRawValue.dateOfRead),
      imageUrl: new FormControl(notificationRawValue.imageUrl),
      originalPrice: new FormControl(notificationRawValue.originalPrice),
      currentPrice: new FormControl(notificationRawValue.currentPrice),
      discount: new FormControl(notificationRawValue.discount),
      discountType: new FormControl(notificationRawValue.discountType),
    });
  }

  getNotification(form: NotificationFormGroup): INotification | NewNotification {
    return form.getRawValue() as INotification | NewNotification;
  }

  resetForm(form: NotificationFormGroup, notification: NotificationFormGroupInput): void {
    const notificationRawValue = { ...this.getFormDefaults(), ...notification };
    form.reset(
      {
        ...notificationRawValue,
        id: { value: notificationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NotificationFormDefaults {
    return {
      id: null,
    };
  }
}
