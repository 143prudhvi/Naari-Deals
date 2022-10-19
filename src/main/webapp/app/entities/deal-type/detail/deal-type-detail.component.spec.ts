import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DealTypeDetailComponent } from './deal-type-detail.component';

describe('DealType Management Detail Component', () => {
  let comp: DealTypeDetailComponent;
  let fixture: ComponentFixture<DealTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DealTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ dealType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DealTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DealTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load dealType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.dealType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
