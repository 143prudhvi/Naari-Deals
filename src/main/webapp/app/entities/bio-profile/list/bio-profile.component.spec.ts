import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { BioProfileService } from '../service/bio-profile.service';

import { BioProfileComponent } from './bio-profile.component';

describe('BioProfile Management Component', () => {
  let comp: BioProfileComponent;
  let fixture: ComponentFixture<BioProfileComponent>;
  let service: BioProfileService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'bio-profile', component: BioProfileComponent }]), HttpClientTestingModule],
      declarations: [BioProfileComponent],
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
      .overrideTemplate(BioProfileComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BioProfileComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(BioProfileService);

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
    expect(comp.bioProfiles?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to bioProfileService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getBioProfileIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getBioProfileIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
