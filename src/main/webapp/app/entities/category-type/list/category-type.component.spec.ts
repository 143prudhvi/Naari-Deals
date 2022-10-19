import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CategoryTypeService } from '../service/category-type.service';

import { CategoryTypeComponent } from './category-type.component';

describe('CategoryType Management Component', () => {
  let comp: CategoryTypeComponent;
  let fixture: ComponentFixture<CategoryTypeComponent>;
  let service: CategoryTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'category-type', component: CategoryTypeComponent }]), HttpClientTestingModule],
      declarations: [CategoryTypeComponent],
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
      .overrideTemplate(CategoryTypeComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CategoryTypeComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(CategoryTypeService);

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
    expect(comp.categoryTypes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to categoryTypeService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getCategoryTypeIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getCategoryTypeIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
