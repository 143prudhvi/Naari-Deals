import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmailSubscriptionDetailComponent } from './email-subscription-detail.component';

describe('EmailSubscription Management Detail Component', () => {
  let comp: EmailSubscriptionDetailComponent;
  let fixture: ComponentFixture<EmailSubscriptionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EmailSubscriptionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ emailSubscription: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EmailSubscriptionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EmailSubscriptionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load emailSubscription on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.emailSubscription).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
