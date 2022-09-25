import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../bio-profile.test-samples';

import { BioProfileFormService } from './bio-profile-form.service';

describe('BioProfile Form Service', () => {
  let service: BioProfileFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BioProfileFormService);
  });

  describe('Service methods', () => {
    describe('createBioProfileFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createBioProfileFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            userId: expect.any(Object),
            firstName: expect.any(Object),
            lastName: expect.any(Object),
            dob: expect.any(Object),
            gender: expect.any(Object),
            imageUrl: expect.any(Object),
          })
        );
      });

      it('passing IBioProfile should create a new form with FormGroup', () => {
        const formGroup = service.createBioProfileFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            userId: expect.any(Object),
            firstName: expect.any(Object),
            lastName: expect.any(Object),
            dob: expect.any(Object),
            gender: expect.any(Object),
            imageUrl: expect.any(Object),
          })
        );
      });
    });

    describe('getBioProfile', () => {
      it('should return NewBioProfile for default BioProfile initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createBioProfileFormGroup(sampleWithNewData);

        const bioProfile = service.getBioProfile(formGroup) as any;

        expect(bioProfile).toMatchObject(sampleWithNewData);
      });

      it('should return NewBioProfile for empty BioProfile initial value', () => {
        const formGroup = service.createBioProfileFormGroup();

        const bioProfile = service.getBioProfile(formGroup) as any;

        expect(bioProfile).toMatchObject({});
      });

      it('should return IBioProfile', () => {
        const formGroup = service.createBioProfileFormGroup(sampleWithRequiredData);

        const bioProfile = service.getBioProfile(formGroup) as any;

        expect(bioProfile).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IBioProfile should not enable id FormControl', () => {
        const formGroup = service.createBioProfileFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewBioProfile should disable id FormControl', () => {
        const formGroup = service.createBioProfileFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
