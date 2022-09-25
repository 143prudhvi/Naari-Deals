import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MemberTypeService } from '../service/member-type.service';

import { MemberTypeComponent } from './member-type.component';

describe('MemberType Management Component', () => {
  let comp: MemberTypeComponent;
  let fixture: ComponentFixture<MemberTypeComponent>;
  let service: MemberTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'member-type', component: MemberTypeComponent }]), HttpClientTestingModule],
      declarations: [MemberTypeComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(MemberTypeComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MemberTypeComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(MemberTypeService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.memberTypes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to memberTypeService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getMemberTypeIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getMemberTypeIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
