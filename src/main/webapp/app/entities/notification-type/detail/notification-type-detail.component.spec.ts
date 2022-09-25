import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NotificationTypeDetailComponent } from './notification-type-detail.component';

describe('NotificationType Management Detail Component', () => {
  let comp: NotificationTypeDetailComponent;
  let fixture: ComponentFixture<NotificationTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NotificationTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ notificationType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NotificationTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NotificationTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load notificationType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.notificationType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
