import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LoginProfileFormService } from './login-profile-form.service';
import { LoginProfileService } from '../service/login-profile.service';
import { ILoginProfile } from '../login-profile.model';

import { LoginProfileUpdateComponent } from './login-profile-update.component';

describe('LoginProfile Management Update Component', () => {
  let comp: LoginProfileUpdateComponent;
  let fixture: ComponentFixture<LoginProfileUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let loginProfileFormService: LoginProfileFormService;
  let loginProfileService: LoginProfileService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LoginProfileUpdateComponent],
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
      .overrideTemplate(LoginProfileUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LoginProfileUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    loginProfileFormService = TestBed.inject(LoginProfileFormService);
    loginProfileService = TestBed.inject(LoginProfileService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const loginProfile: ILoginProfile = { id: 456 };

      activatedRoute.data = of({ loginProfile });
      comp.ngOnInit();

      expect(comp.loginProfile).toEqual(loginProfile);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoginProfile>>();
      const loginProfile = { id: 123 };
      jest.spyOn(loginProfileFormService, 'getLoginProfile').mockReturnValue(loginProfile);
      jest.spyOn(loginProfileService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loginProfile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loginProfile }));
      saveSubject.complete();

      // THEN
      expect(loginProfileFormService.getLoginProfile).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(loginProfileService.update).toHaveBeenCalledWith(expect.objectContaining(loginProfile));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoginProfile>>();
      const loginProfile = { id: 123 };
      jest.spyOn(loginProfileFormService, 'getLoginProfile').mockReturnValue({ id: null });
      jest.spyOn(loginProfileService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loginProfile: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loginProfile }));
      saveSubject.complete();

      // THEN
      expect(loginProfileFormService.getLoginProfile).toHaveBeenCalled();
      expect(loginProfileService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoginProfile>>();
      const loginProfile = { id: 123 };
      jest.spyOn(loginProfileService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loginProfile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(loginProfileService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
