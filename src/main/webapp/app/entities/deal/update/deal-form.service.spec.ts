import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../deal.test-samples';

import { DealFormService } from './deal-form.service';

describe('Deal Form Service', () => {
  let service: DealFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DealFormService);
  });

  describe('Service methods', () => {
    describe('createDealFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDealFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            description: expect.any(Object),
            imageUrl: expect.any(Object),
            dealUrl: expect.any(Object),
            postedBy: expect.any(Object),
            postedDate: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            originalPrice: expect.any(Object),
            currentPrice: expect.any(Object),
            discount: expect.any(Object),
            discountType: expect.any(Object),
            active: expect.any(Object),
            approved: expect.any(Object),
            country: expect.any(Object),
            city: expect.any(Object),
            pinCode: expect.any(Object),
            merchant: expect.any(Object),
            tags: expect.any(Object),
            brand: expect.any(Object),
            expired: expect.any(Object),
          })
        );
      });

      it('passing IDeal should create a new form with FormGroup', () => {
        const formGroup = service.createDealFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            description: expect.any(Object),
            imageUrl: expect.any(Object),
            dealUrl: expect.any(Object),
            postedBy: expect.any(Object),
            postedDate: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            originalPrice: expect.any(Object),
            currentPrice: expect.any(Object),
            discount: expect.any(Object),
            discountType: expect.any(Object),
            active: expect.any(Object),
            approved: expect.any(Object),
            country: expect.any(Object),
            city: expect.any(Object),
            pinCode: expect.any(Object),
            merchant: expect.any(Object),
            tags: expect.any(Object),
            brand: expect.any(Object),
            expired: expect.any(Object),
          })
        );
      });
    });

    describe('getDeal', () => {
      it('should return NewDeal for default Deal initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDealFormGroup(sampleWithNewData);

        const deal = service.getDeal(formGroup) as any;

        expect(deal).toMatchObject(sampleWithNewData);
      });

      it('should return NewDeal for empty Deal initial value', () => {
        const formGroup = service.createDealFormGroup();

        const deal = service.getDeal(formGroup) as any;

        expect(deal).toMatchObject({});
      });

      it('should return IDeal', () => {
        const formGroup = service.createDealFormGroup(sampleWithRequiredData);

        const deal = service.getDeal(formGroup) as any;

        expect(deal).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDeal should not enable id FormControl', () => {
        const formGroup = service.createDealFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDeal should disable id FormControl', () => {
        const formGroup = service.createDealFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
