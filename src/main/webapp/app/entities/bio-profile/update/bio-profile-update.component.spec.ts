import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BioProfileFormService } from './bio-profile-form.service';
import { BioProfileService } from '../service/bio-profile.service';
import { IBioProfile } from '../bio-profile.model';

import { BioProfileUpdateComponent } from './bio-profile-update.component';

describe('BioProfile Management Update Component', () => {
  let comp: BioProfileUpdateComponent;
  let fixture: ComponentFixture<BioProfileUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bioProfileFormService: BioProfileFormService;
  let bioProfileService: BioProfileService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BioProfileUpdateComponent],
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
      .overrideTemplate(BioProfileUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BioProfileUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bioProfileFormService = TestBed.inject(BioProfileFormService);
    bioProfileService = TestBed.inject(BioProfileService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const bioProfile: IBioProfile = { id: 456 };

      activatedRoute.data = of({ bioProfile });
      comp.ngOnInit();

      expect(comp.bioProfile).toEqual(bioProfile);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBioProfile>>();
      const bioProfile = { id: 123 };
      jest.spyOn(bioProfileFormService, 'getBioProfile').mockReturnValue(bioProfile);
      jest.spyOn(bioProfileService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bioProfile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bioProfile }));
      saveSubject.complete();

      // THEN
      expect(bioProfileFormService.getBioProfile).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(bioProfileService.update).toHaveBeenCalledWith(expect.objectContaining(bioProfile));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBioProfile>>();
      const bioProfile = { id: 123 };
      jest.spyOn(bioProfileFormService, 'getBioProfile').mockReturnValue({ id: null });
      jest.spyOn(bioProfileService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bioProfile: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bioProfile }));
      saveSubject.complete();

      // THEN
      expect(bioProfileFormService.getBioProfile).toHaveBeenCalled();
      expect(bioProfileService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBioProfile>>();
      const bioProfile = { id: 123 };
      jest.spyOn(bioProfileService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bioProfile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bioProfileService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
