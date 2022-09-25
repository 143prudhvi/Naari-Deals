import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DealTypeFormService } from './deal-type-form.service';
import { DealTypeService } from '../service/deal-type.service';
import { IDealType } from '../deal-type.model';

import { DealTypeUpdateComponent } from './deal-type-update.component';

describe('DealType Management Update Component', () => {
  let comp: DealTypeUpdateComponent;
  let fixture: ComponentFixture<DealTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dealTypeFormService: DealTypeFormService;
  let dealTypeService: DealTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DealTypeUpdateComponent],
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
      .overrideTemplate(DealTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DealTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dealTypeFormService = TestBed.inject(DealTypeFormService);
    dealTypeService = TestBed.inject(DealTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const dealType: IDealType = { id: 456 };

      activatedRoute.data = of({ dealType });
      comp.ngOnInit();

      expect(comp.dealType).toEqual(dealType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDealType>>();
      const dealType = { id: 123 };
      jest.spyOn(dealTypeFormService, 'getDealType').mockReturnValue(dealType);
      jest.spyOn(dealTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dealType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dealType }));
      saveSubject.complete();

      // THEN
      expect(dealTypeFormService.getDealType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(dealTypeService.update).toHaveBeenCalledWith(expect.objectContaining(dealType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDealType>>();
      const dealType = { id: 123 };
      jest.spyOn(dealTypeFormService, 'getDealType').mockReturnValue({ id: null });
      jest.spyOn(dealTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dealType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dealType }));
      saveSubject.complete();

      // THEN
      expect(dealTypeFormService.getDealType).toHaveBeenCalled();
      expect(dealTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDealType>>();
      const dealType = { id: 123 };
      jest.spyOn(dealTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dealType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dealTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
