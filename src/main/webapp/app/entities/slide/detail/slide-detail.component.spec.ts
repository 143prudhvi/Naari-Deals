import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SlideDetailComponent } from './slide-detail.component';

describe('Slide Management Detail Component', () => {
  let comp: SlideDetailComponent;
  let fixture: ComponentFixture<SlideDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SlideDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ slide: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SlideDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SlideDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load slide on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.slide).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
