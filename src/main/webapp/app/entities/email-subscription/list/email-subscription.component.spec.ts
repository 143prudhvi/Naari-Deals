import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { EmailSubscriptionService } from '../service/email-subscription.service';

import { EmailSubscriptionComponent } from './email-subscription.component';

describe('EmailSubscription Management Component', () => {
  let comp: EmailSubscriptionComponent;
  let fixture: ComponentFixture<EmailSubscriptionComponent>;
  let service: EmailSubscriptionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'email-subscription', component: EmailSubscriptionComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [EmailSubscriptionComponent],
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
      .overrideTemplate(EmailSubscriptionComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmailSubscriptionComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(EmailSubscriptionService);

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
    expect(comp.emailSubscriptions?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to emailSubscriptionService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getEmailSubscriptionIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getEmailSubscriptionIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
