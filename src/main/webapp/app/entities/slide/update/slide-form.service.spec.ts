import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../slide.test-samples';

import { SlideFormService } from './slide-form.service';

describe('Slide Form Service', () => {
  let service: SlideFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SlideFormService);
  });

  describe('Service methods', () => {
    describe('createSlideFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSlideFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            subTitle: expect.any(Object),
            imageUrl: expect.any(Object),
            status: expect.any(Object),
            country: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            dealUrl: expect.any(Object),
          })
        );
      });

      it('passing ISlide should create a new form with FormGroup', () => {
        const formGroup = service.createSlideFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            subTitle: expect.any(Object),
            imageUrl: expect.any(Object),
            status: expect.any(Object),
            country: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            dealUrl: expect.any(Object),
          })
        );
      });
    });

    describe('getSlide', () => {
      it('should return NewSlide for default Slide initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSlideFormGroup(sampleWithNewData);

        const slide = service.getSlide(formGroup) as any;

        expect(slide).toMatchObject(sampleWithNewData);
      });

      it('should return NewSlide for empty Slide initial value', () => {
        const formGroup = service.createSlideFormGroup();

        const slide = service.getSlide(formGroup) as any;

        expect(slide).toMatchObject({});
      });

      it('should return ISlide', () => {
        const formGroup = service.createSlideFormGroup(sampleWithRequiredData);

        const slide = service.getSlide(formGroup) as any;

        expect(slide).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISlide should not enable id FormControl', () => {
        const formGroup = service.createSlideFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSlide should disable id FormControl', () => {
        const formGroup = service.createSlideFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
