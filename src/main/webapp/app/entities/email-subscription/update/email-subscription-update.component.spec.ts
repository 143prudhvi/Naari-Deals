import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EmailSubscriptionFormService } from './email-subscription-form.service';
import { EmailSubscriptionService } from '../service/email-subscription.service';
import { IEmailSubscription } from '../email-subscription.model';

import { EmailSubscriptionUpdateComponent } from './email-subscription-update.component';

describe('EmailSubscription Management Update Component', () => {
  let comp: EmailSubscriptionUpdateComponent;
  let fixture: ComponentFixture<EmailSubscriptionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let emailSubscriptionFormService: EmailSubscriptionFormService;
  let emailSubscriptionService: EmailSubscriptionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EmailSubscriptionUpdateComponent],
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
      .overrideTemplate(EmailSubscriptionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmailSubscriptionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    emailSubscriptionFormService = TestBed.inject(EmailSubscriptionFormService);
    emailSubscriptionService = TestBed.inject(EmailSubscriptionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const emailSubscription: IEmailSubscription = { id: 456 };

      activatedRoute.data = of({ emailSubscription });
      comp.ngOnInit();

      expect(comp.emailSubscription).toEqual(emailSubscription);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmailSubscription>>();
      const emailSubscription = { id: 123 };
      jest.spyOn(emailSubscriptionFormService, 'getEmailSubscription').mockReturnValue(emailSubscription);
      jest.spyOn(emailSubscriptionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emailSubscription });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: emailSubscription }));
      saveSubject.complete();

      // THEN
      expect(emailSubscriptionFormService.getEmailSubscription).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(emailSubscriptionService.update).toHaveBeenCalledWith(expect.objectContaining(emailSubscription));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmailSubscription>>();
      const emailSubscription = { id: 123 };
      jest.spyOn(emailSubscriptionFormService, 'getEmailSubscription').mockReturnValue({ id: null });
      jest.spyOn(emailSubscriptionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emailSubscription: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: emailSubscription }));
      saveSubject.complete();

      // THEN
      expect(emailSubscriptionFormService.getEmailSubscription).toHaveBeenCalled();
      expect(emailSubscriptionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmailSubscription>>();
      const emailSubscription = { id: 123 };
      jest.spyOn(emailSubscriptionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emailSubscription });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(emailSubscriptionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
