import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DealFormService } from './deal-form.service';
import { DealService } from '../service/deal.service';
import { IDeal } from '../deal.model';

import { DealUpdateComponent } from './deal-update.component';

describe('Deal Management Update Component', () => {
  let comp: DealUpdateComponent;
  let fixture: ComponentFixture<DealUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dealFormService: DealFormService;
  let dealService: DealService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DealUpdateComponent],
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
      .overrideTemplate(DealUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DealUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dealFormService = TestBed.inject(DealFormService);
    dealService = TestBed.inject(DealService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const deal: IDeal = { id: 456 };

      activatedRoute.data = of({ deal });
      comp.ngOnInit();

      expect(comp.deal).toEqual(deal);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDeal>>();
      const deal = { id: 123 };
      jest.spyOn(dealFormService, 'getDeal').mockReturnValue(deal);
      jest.spyOn(dealService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ deal });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: deal }));
      saveSubject.complete();

      // THEN
      expect(dealFormService.getDeal).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(dealService.update).toHaveBeenCalledWith(expect.objectContaining(deal));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDeal>>();
      const deal = { id: 123 };
      jest.spyOn(dealFormService, 'getDeal').mockReturnValue({ id: null });
      jest.spyOn(dealService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ deal: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: deal }));
      saveSubject.complete();

      // THEN
      expect(dealFormService.getDeal).toHaveBeenCalled();
      expect(dealService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDeal>>();
      const deal = { id: 123 };
      jest.spyOn(dealService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ deal });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dealService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
