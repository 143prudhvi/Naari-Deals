import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../member-type.test-samples';

import { MemberTypeFormService } from './member-type-form.service';

describe('MemberType Form Service', () => {
  let service: MemberTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MemberTypeFormService);
  });

  describe('Service methods', () => {
    describe('createMemberTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMemberTypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            memberType: expect.any(Object),
            description: expect.any(Object),
            imageUrl: expect.any(Object),
          })
        );
      });

      it('passing IMemberType should create a new form with FormGroup', () => {
        const formGroup = service.createMemberTypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            memberType: expect.any(Object),
            description: expect.any(Object),
            imageUrl: expect.any(Object),
          })
        );
      });
    });

    describe('getMemberType', () => {
      it('should return NewMemberType for default MemberType initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMemberTypeFormGroup(sampleWithNewData);

        const memberType = service.getMemberType(formGroup) as any;

        expect(memberType).toMatchObject(sampleWithNewData);
      });

      it('should return NewMemberType for empty MemberType initial value', () => {
        const formGroup = service.createMemberTypeFormGroup();

        const memberType = service.getMemberType(formGroup) as any;

        expect(memberType).toMatchObject({});
      });

      it('should return IMemberType', () => {
        const formGroup = service.createMemberTypeFormGroup(sampleWithRequiredData);

        const memberType = service.getMemberType(formGroup) as any;

        expect(memberType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMemberType should not enable id FormControl', () => {
        const formGroup = service.createMemberTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMemberType should disable id FormControl', () => {
        const formGroup = service.createMemberTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
