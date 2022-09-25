import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BioProfileDetailComponent } from './bio-profile-detail.component';

describe('BioProfile Management Detail Component', () => {
  let comp: BioProfileDetailComponent;
  let fixture: ComponentFixture<BioProfileDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BioProfileDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ bioProfile: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BioProfileDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BioProfileDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bioProfile on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.bioProfile).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
