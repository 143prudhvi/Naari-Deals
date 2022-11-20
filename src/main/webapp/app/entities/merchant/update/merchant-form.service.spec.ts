import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../merchant.test-samples';

import { MerchantFormService } from './merchant-form.service';

describe('Merchant Form Service', () => {
  let service: MerchantFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MerchantFormService);
  });

  describe('Service methods', () => {
    describe('createMerchantFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMerchantFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            title: expect.any(Object),
            subTitle: expect.any(Object),
            address: expect.any(Object),
            phone: expect.any(Object),
            country: expect.any(Object),
            city: expect.any(Object),
            imageUrl: expect.any(Object),
            type: expect.any(Object),
            location: expect.any(Object),
            siteUrl: expect.any(Object),
            status: expect.any(Object),
          })
        );
      });

      it('passing IMerchant should create a new form with FormGroup', () => {
        const formGroup = service.createMerchantFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            title: expect.any(Object),
            subTitle: expect.any(Object),
            address: expect.any(Object),
            phone: expect.any(Object),
            country: expect.any(Object),
            city: expect.any(Object),
            imageUrl: expect.any(Object),
            type: expect.any(Object),
            location: expect.any(Object),
            siteUrl: expect.any(Object),
            status: expect.any(Object),
          })
        );
      });
    });

    describe('getMerchant', () => {
      it('should return NewMerchant for default Merchant initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMerchantFormGroup(sampleWithNewData);

        const merchant = service.getMerchant(formGroup) as any;

        expect(merchant).toMatchObject(sampleWithNewData);
      });

      it('should return NewMerchant for empty Merchant initial value', () => {
        const formGroup = service.createMerchantFormGroup();

        const merchant = service.getMerchant(formGroup) as any;

        expect(merchant).toMatchObject({});
      });

      it('should return IMerchant', () => {
        const formGroup = service.createMerchantFormGroup(sampleWithRequiredData);

        const merchant = service.getMerchant(formGroup) as any;

        expect(merchant).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMerchant should not enable id FormControl', () => {
        const formGroup = service.createMerchantFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMerchant should disable id FormControl', () => {
        const formGroup = service.createMerchantFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
