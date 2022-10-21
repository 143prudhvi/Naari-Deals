import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SlideFormService } from './slide-form.service';
import { SlideService } from '../service/slide.service';
import { ISlide } from '../slide.model';

import { SlideUpdateComponent } from './slide-update.component';

describe('Slide Management Update Component', () => {
  let comp: SlideUpdateComponent;
  let fixture: ComponentFixture<SlideUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let slideFormService: SlideFormService;
  let slideService: SlideService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SlideUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(SlideUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SlideUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    slideFormService = TestBed.inject(SlideFormService);
    slideService = TestBed.inject(SlideService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const slide: ISlide = { id: 456 };

      activatedRoute.data = of({ slide });
      comp.ngOnInit();

      expect(comp.slide).toEqual(slide);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISlide>>();
      const slide = { id: 123 };
      jest.spyOn(slideFormService, 'getSlide').mockReturnValue(slide);
      jest.spyOn(slideService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ slide });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: slide }));
      saveSubject.complete();

      // THEN
      expect(slideFormService.getSlide).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(slideService.update).toHaveBeenCalledWith(expect.objectContaining(slide));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISlide>>();
      const slide = { id: 123 };
      jest.spyOn(slideFormService, 'getSlide').mockReturnValue({ id: null });
      jest.spyOn(slideService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ slide: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: slide }));
      saveSubject.complete();

      // THEN
      expect(slideFormService.getSlide).toHaveBeenCalled();
      expect(slideService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISlide>>();
      const slide = { id: 123 };
      jest.spyOn(slideService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ slide });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(slideService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
