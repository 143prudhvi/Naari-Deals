import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../notification-type.test-samples';

import { NotificationTypeFormService } from './notification-type-form.service';

describe('NotificationType Form Service', () => {
  let service: NotificationTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NotificationTypeFormService);
  });

  describe('Service methods', () => {
    describe('createNotificationTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNotificationTypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            type: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });

      it('passing INotificationType should create a new form with FormGroup', () => {
        const formGroup = service.createNotificationTypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            type: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });
    });

    describe('getNotificationType', () => {
      it('should return NewNotificationType for default NotificationType initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createNotificationTypeFormGroup(sampleWithNewData);

        const notificationType = service.getNotificationType(formGroup) as any;

        expect(notificationType).toMatchObject(sampleWithNewData);
      });

      it('should return NewNotificationType for empty NotificationType initial value', () => {
        const formGroup = service.createNotificationTypeFormGroup();

        const notificationType = service.getNotificationType(formGroup) as any;

        expect(notificationType).toMatchObject({});
      });

      it('should return INotificationType', () => {
        const formGroup = service.createNotificationTypeFormGroup(sampleWithRequiredData);

        const notificationType = service.getNotificationType(formGroup) as any;

        expect(notificationType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INotificationType should not enable id FormControl', () => {
        const formGroup = service.createNotificationTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewNotificationType should disable id FormControl', () => {
        const formGroup = service.createNotificationTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
