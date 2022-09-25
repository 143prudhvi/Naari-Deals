import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MemberTypeFormService } from './member-type-form.service';
import { MemberTypeService } from '../service/member-type.service';
import { IMemberType } from '../member-type.model';

import { MemberTypeUpdateComponent } from './member-type-update.component';

describe('MemberType Management Update Component', () => {
  let comp: MemberTypeUpdateComponent;
  let fixture: ComponentFixture<MemberTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let memberTypeFormService: MemberTypeFormService;
  let memberTypeService: MemberTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MemberTypeUpdateComponent],
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
      .overrideTemplate(MemberTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MemberTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    memberTypeFormService = TestBed.inject(MemberTypeFormService);
    memberTypeService = TestBed.inject(MemberTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const memberType: IMemberType = { id: 456 };

      activatedRoute.data = of({ memberType });
      comp.ngOnInit();

      expect(comp.memberType).toEqual(memberType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMemberType>>();
      const memberType = { id: 123 };
      jest.spyOn(memberTypeFormService, 'getMemberType').mockReturnValue(memberType);
      jest.spyOn(memberTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ memberType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: memberType }));
      saveSubject.complete();

      // THEN
      expect(memberTypeFormService.getMemberType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(memberTypeService.update).toHaveBeenCalledWith(expect.objectContaining(memberType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMemberType>>();
      const memberType = { id: 123 };
      jest.spyOn(memberTypeFormService, 'getMemberType').mockReturnValue({ id: null });
      jest.spyOn(memberTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ memberType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: memberType }));
      saveSubject.complete();

      // THEN
      expect(memberTypeFormService.getMemberType).toHaveBeenCalled();
      expect(memberTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMemberType>>();
      const memberType = { id: 123 };
      jest.spyOn(memberTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ memberType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(memberTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
