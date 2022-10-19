import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../category-type.test-samples';

import { CategoryTypeFormService } from './category-type-form.service';

describe('CategoryType Form Service', () => {
  let service: CategoryTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CategoryTypeFormService);
  });

  describe('Service methods', () => {
    describe('createCategoryTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCategoryTypeFormGroup();

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

      it('passing ICategoryType should create a new form with FormGroup', () => {
        const formGroup = service.createCategoryTypeFormGroup(sampleWithRequiredData);

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

    describe('getCategoryType', () => {
      it('should return NewCategoryType for default CategoryType initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCategoryTypeFormGroup(sampleWithNewData);

        const categoryType = service.getCategoryType(formGroup) as any;

        expect(categoryType).toMatchObject(sampleWithNewData);
      });

      it('should return NewCategoryType for empty CategoryType initial value', () => {
        const formGroup = service.createCategoryTypeFormGroup();

        const categoryType = service.getCategoryType(formGroup) as any;

        expect(categoryType).toMatchObject({});
      });

      it('should return ICategoryType', () => {
        const formGroup = service.createCategoryTypeFormGroup(sampleWithRequiredData);

        const categoryType = service.getCategoryType(formGroup) as any;

        expect(categoryType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICategoryType should not enable id FormControl', () => {
        const formGroup = service.createCategoryTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCategoryType should disable id FormControl', () => {
        const formGroup = service.createCategoryTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
