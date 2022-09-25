import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILoginProfile } from '../login-profile.model';

@Component({
  selector: 'jhi-login-profile-detail',
  templateUrl: './login-profile-detail.component.html',
})
export class LoginProfileDetailComponent implements OnInit {
  loginProfile: ILoginProfile | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loginProfile }) => {
      this.loginProfile = loginProfile;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
