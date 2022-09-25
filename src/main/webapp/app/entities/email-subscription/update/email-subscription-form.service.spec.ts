import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../email-subscription.test-samples';

import { EmailSubscriptionFormService } from './email-subscription-form.service';

describe('EmailSubscription Form Service', () => {
  let service: EmailSubscriptionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmailSubscriptionFormService);
  });

  describe('Service methods', () => {
    describe('createEmailSubscriptionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEmailSubscriptionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            email: expect.any(Object),
            country: expect.any(Object),
          })
        );
      });

      it('passing IEmailSubscription should create a new form with FormGroup', () => {
        const formGroup = service.createEmailSubscriptionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            email: expect.any(Object),
            country: expect.any(Object),
          })
        );
      });
    });

    describe('getEmailSubscription', () => {
      it('should return NewEmailSubscription for default EmailSubscription initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEmailSubscriptionFormGroup(sampleWithNewData);

        const emailSubscription = service.getEmailSubscription(formGroup) as any;

        expect(emailSubscription).toMatchObject(sampleWithNewData);
      });

      it('should return NewEmailSubscription for empty EmailSubscription initial value', () => {
        const formGroup = service.createEmailSubscriptionFormGroup();

        const emailSubscription = service.getEmailSubscription(formGroup) as any;

        expect(emailSubscription).toMatchObject({});
      });

      it('should return IEmailSubscription', () => {
        const formGroup = service.createEmailSubscriptionFormGroup(sampleWithRequiredData);

        const emailSubscription = service.getEmailSubscription(formGroup) as any;

        expect(emailSubscription).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEmailSubscription should not enable id FormControl', () => {
        const formGroup = service.createEmailSubscriptionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEmailSubscription should disable id FormControl', () => {
        const formGroup = service.createEmailSubscriptionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
