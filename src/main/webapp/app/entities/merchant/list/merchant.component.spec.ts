import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MerchantService } from '../service/merchant.service';

import { MerchantComponent } from './merchant.component';

describe('Merchant Management Component', () => {
  let comp: MerchantComponent;
  let fixture: ComponentFixture<MerchantComponent>;
  let service: MerchantService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'merchant', component: MerchantComponent }]), HttpClientTestingModule],
      declarations: [MerchantComponent],
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
      .overrideTemplate(MerchantComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MerchantComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(MerchantService);

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
    expect(comp.merchants?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to merchantService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getMerchantIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getMerchantIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
