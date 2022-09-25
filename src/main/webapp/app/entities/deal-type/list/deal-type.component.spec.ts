import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DealTypeService } from '../service/deal-type.service';

import { DealTypeComponent } from './deal-type.component';

describe('DealType Management Component', () => {
  let comp: DealTypeComponent;
  let fixture: ComponentFixture<DealTypeComponent>;
  let service: DealTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'deal-type', component: DealTypeComponent }]), HttpClientTestingModule],
      declarations: [DealTypeComponent],
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
      .overrideTemplate(DealTypeComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DealTypeComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DealTypeService);

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
    expect(comp.dealTypes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to dealTypeService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getDealTypeIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getDealTypeIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
