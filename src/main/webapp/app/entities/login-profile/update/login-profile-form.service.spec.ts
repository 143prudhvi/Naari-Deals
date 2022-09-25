import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../login-profile.test-samples';

import { LoginProfileFormService } from './login-profile-form.service';

describe('LoginProfile Form Service', () => {
  let service: LoginProfileFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoginProfileFormService);
  });

  describe('Service methods', () => {
    describe('createLoginProfileFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLoginProfileFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            userName: expect.any(Object),
            userId: expect.any(Object),
            memberType: expect.any(Object),
            phoneNumber: expect.any(Object),
            emailId: expect.any(Object),
            password: expect.any(Object),
            activationStatus: expect.any(Object),
            activationCode: expect.any(Object),
          })
        );
      });

      it('passing ILoginProfile should create a new form with FormGroup', () => {
        const formGroup = service.createLoginProfileFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            userName: expect.any(Object),
            userId: expect.any(Object),
            memberType: expect.any(Object),
            phoneNumber: expect.any(Object),
            emailId: expect.any(Object),
            password: expect.any(Object),
            activationStatus: expect.any(Object),
            activationCode: expect.any(Object),
          })
        );
      });
    });

    describe('getLoginProfile', () => {
      it('should return NewLoginProfile for default LoginProfile initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLoginProfileFormGroup(sampleWithNewData);

        const loginProfile = service.getLoginProfile(formGroup) as any;

        expect(loginProfile).toMatchObject(sampleWithNewData);
      });

      it('should return NewLoginProfile for empty LoginProfile initial value', () => {
        const formGroup = service.createLoginProfileFormGroup();

        const loginProfile = service.getLoginProfile(formGroup) as any;

        expect(loginProfile).toMatchObject({});
      });

      it('should return ILoginProfile', () => {
        const formGroup = service.createLoginProfileFormGroup(sampleWithRequiredData);

        const loginProfile = service.getLoginProfile(formGroup) as any;

        expect(loginProfile).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILoginProfile should not enable id FormControl', () => {
        const formGroup = service.createLoginProfileFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLoginProfile should disable id FormControl', () => {
        const formGroup = service.createLoginProfileFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
