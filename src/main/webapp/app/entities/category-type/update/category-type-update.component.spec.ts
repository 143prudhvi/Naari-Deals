import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CategoryTypeFormService } from './category-type-form.service';
import { CategoryTypeService } from '../service/category-type.service';
import { ICategoryType } from '../category-type.model';

import { CategoryTypeUpdateComponent } from './category-type-update.component';

describe('CategoryType Management Update Component', () => {
  let comp: CategoryTypeUpdateComponent;
  let fixture: ComponentFixture<CategoryTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let categoryTypeFormService: CategoryTypeFormService;
  let categoryTypeService: CategoryTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CategoryTypeUpdateComponent],
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
      .overrideTemplate(CategoryTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CategoryTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    categoryTypeFormService = TestBed.inject(CategoryTypeFormService);
    categoryTypeService = TestBed.inject(CategoryTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const categoryType: ICategoryType = { id: 456 };

      activatedRoute.data = of({ categoryType });
      comp.ngOnInit();

      expect(comp.categoryType).toEqual(categoryType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategoryType>>();
      const categoryType = { id: 123 };
      jest.spyOn(categoryTypeFormService, 'getCategoryType').mockReturnValue(categoryType);
      jest.spyOn(categoryTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categoryType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categoryType }));
      saveSubject.complete();

      // THEN
      expect(categoryTypeFormService.getCategoryType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(categoryTypeService.update).toHaveBeenCalledWith(expect.objectContaining(categoryType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategoryType>>();
      const categoryType = { id: 123 };
      jest.spyOn(categoryTypeFormService, 'getCategoryType').mockReturnValue({ id: null });
      jest.spyOn(categoryTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categoryType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: categoryType }));
      saveSubject.complete();

      // THEN
      expect(categoryTypeFormService.getCategoryType).toHaveBeenCalled();
      expect(categoryTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICategoryType>>();
      const categoryType = { id: 123 };
      jest.spyOn(categoryTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ categoryType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(categoryTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
