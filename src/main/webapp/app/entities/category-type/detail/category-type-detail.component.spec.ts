import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CategoryTypeDetailComponent } from './category-type-detail.component';

describe('CategoryType Management Detail Component', () => {
  let comp: CategoryTypeDetailComponent;
  let fixture: ComponentFixture<CategoryTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CategoryTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ categoryType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CategoryTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CategoryTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load categoryType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.categoryType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
