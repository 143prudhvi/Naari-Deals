import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { SlideService } from '../service/slide.service';

import { SlideComponent } from './slide.component';

describe('Slide Management Component', () => {
  let comp: SlideComponent;
  let fixture: ComponentFixture<SlideComponent>;
  let service: SlideService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'slide', component: SlideComponent }]), HttpClientTestingModule],
      declarations: [SlideComponent],
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
      .overrideTemplate(SlideComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SlideComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SlideService);

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
    expect(comp.slides?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to slideService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getSlideIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getSlideIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
