import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MerchantFormService } from './merchant-form.service';
import { MerchantService } from '../service/merchant.service';
import { IMerchant } from '../merchant.model';

import { MerchantUpdateComponent } from './merchant-update.component';

describe('Merchant Management Update Component', () => {
  let comp: MerchantUpdateComponent;
  let fixture: ComponentFixture<MerchantUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let merchantFormService: MerchantFormService;
  let merchantService: MerchantService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MerchantUpdateComponent],
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
      .overrideTemplate(MerchantUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MerchantUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    merchantFormService = TestBed.inject(MerchantFormService);
    merchantService = TestBed.inject(MerchantService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const merchant: IMerchant = { id: 456 };

      activatedRoute.data = of({ merchant });
      comp.ngOnInit();

      expect(comp.merchant).toEqual(merchant);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMerchant>>();
      const merchant = { id: 123 };
      jest.spyOn(merchantFormService, 'getMerchant').mockReturnValue(merchant);
      jest.spyOn(merchantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ merchant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: merchant }));
      saveSubject.complete();

      // THEN
      expect(merchantFormService.getMerchant).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(merchantService.update).toHaveBeenCalledWith(expect.objectContaining(merchant));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMerchant>>();
      const merchant = { id: 123 };
      jest.spyOn(merchantFormService, 'getMerchant').mockReturnValue({ id: null });
      jest.spyOn(merchantService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ merchant: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: merchant }));
      saveSubject.complete();

      // THEN
      expect(merchantFormService.getMerchant).toHaveBeenCalled();
      expect(merchantService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMerchant>>();
      const merchant = { id: 123 };
      jest.spyOn(merchantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ merchant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(merchantService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
