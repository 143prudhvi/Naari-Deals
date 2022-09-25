import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LoginProfileDetailComponent } from './login-profile-detail.component';

describe('LoginProfile Management Detail Component', () => {
  let comp: LoginProfileDetailComponent;
  let fixture: ComponentFixture<LoginProfileDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LoginProfileDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ loginProfile: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(LoginProfileDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(LoginProfileDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load loginProfile on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.loginProfile).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
