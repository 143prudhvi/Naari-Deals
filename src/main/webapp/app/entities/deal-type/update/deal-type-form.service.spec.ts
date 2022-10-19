import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../deal-type.test-samples';

import { DealTypeFormService } from './deal-type-form.service';

describe('DealType Form Service', () => {
  let service: DealTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DealTypeFormService);
  });

  describe('Service methods', () => {
    describe('createDealTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDealTypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            subTitle: expect.any(Object),
            icon: expect.any(Object),
            bgColor: expect.any(Object),
            country: expect.any(Object),
            code: expect.any(Object),
            status: expect.any(Object),
          })
        );
      });

      it('passing IDealType should create a new form with FormGroup', () => {
        const formGroup = service.createDealTypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            subTitle: expect.any(Object),
            icon: expect.any(Object),
            bgColor: expect.any(Object),
            country: expect.any(Object),
            code: expect.any(Object),
            status: expect.any(Object),
          })
        );
      });
    });

    describe('getDealType', () => {
      it('should return NewDealType for default DealType initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDealTypeFormGroup(sampleWithNewData);

        const dealType = service.getDealType(formGroup) as any;

        expect(dealType).toMatchObject(sampleWithNewData);
      });

      it('should return NewDealType for empty DealType initial value', () => {
        const formGroup = service.createDealTypeFormGroup();

        const dealType = service.getDealType(formGroup) as any;

        expect(dealType).toMatchObject({});
      });

      it('should return IDealType', () => {
        const formGroup = service.createDealTypeFormGroup(sampleWithRequiredData);

        const dealType = service.getDealType(formGroup) as any;

        expect(dealType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDealType should not enable id FormControl', () => {
        const formGroup = service.createDealTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDealType should disable id FormControl', () => {
        const formGroup = service.createDealTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
