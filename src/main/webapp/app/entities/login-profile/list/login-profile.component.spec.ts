import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { LoginProfileService } from '../service/login-profile.service';

import { LoginProfileComponent } from './login-profile.component';

describe('LoginProfile Management Component', () => {
  let comp: LoginProfileComponent;
  let fixture: ComponentFixture<LoginProfileComponent>;
  let service: LoginProfileService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'login-profile', component: LoginProfileComponent }]), HttpClientTestingModule],
      declarations: [LoginProfileComponent],
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
      .overrideTemplate(LoginProfileComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LoginProfileComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(LoginProfileService);

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
    expect(comp.loginProfiles?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to loginProfileService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getLoginProfileIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getLoginProfileIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
