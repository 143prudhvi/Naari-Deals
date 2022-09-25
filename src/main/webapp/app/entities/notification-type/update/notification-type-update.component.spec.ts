import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NotificationTypeFormService } from './notification-type-form.service';
import { NotificationTypeService } from '../service/notification-type.service';
import { INotificationType } from '../notification-type.model';

import { NotificationTypeUpdateComponent } from './notification-type-update.component';

describe('NotificationType Management Update Component', () => {
  let comp: NotificationTypeUpdateComponent;
  let fixture: ComponentFixture<NotificationTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let notificationTypeFormService: NotificationTypeFormService;
  let notificationTypeService: NotificationTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NotificationTypeUpdateComponent],
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
      .overrideTemplate(NotificationTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NotificationTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    notificationTypeFormService = TestBed.inject(NotificationTypeFormService);
    notificationTypeService = TestBed.inject(NotificationTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const notificationType: INotificationType = { id: 456 };

      activatedRoute.data = of({ notificationType });
      comp.ngOnInit();

      expect(comp.notificationType).toEqual(notificationType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INotificationType>>();
      const notificationType = { id: 123 };
      jest.spyOn(notificationTypeFormService, 'getNotificationType').mockReturnValue(notificationType);
      jest.spyOn(notificationTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ notificationType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: notificationType }));
      saveSubject.complete();

      // THEN
      expect(notificationTypeFormService.getNotificationType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(notificationTypeService.update).toHaveBeenCalledWith(expect.objectContaining(notificationType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INotificationType>>();
      const notificationType = { id: 123 };
      jest.spyOn(notificationTypeFormService, 'getNotificationType').mockReturnValue({ id: null });
      jest.spyOn(notificationTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ notificationType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: notificationType }));
      saveSubject.complete();

      // THEN
      expect(notificationTypeFormService.getNotificationType).toHaveBeenCalled();
      expect(notificationTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INotificationType>>();
      const notificationType = { id: 123 };
      jest.spyOn(notificationTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ notificationType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(notificationTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
