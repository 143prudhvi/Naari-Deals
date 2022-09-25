import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { LoginProfileFormService, LoginProfileFormGroup } from './login-profile-form.service';
import { ILoginProfile } from '../login-profile.model';
import { LoginProfileService } from '../service/login-profile.service';

@Component({
  selector: 'jhi-login-profile-update',
  templateUrl: './login-profile-update.component.html',
})
export class LoginProfileUpdateComponent implements OnInit {
  isSaving = false;
  loginProfile: ILoginProfile | null = null;

  editForm: LoginProfileFormGroup = this.loginProfileFormService.createLoginProfileFormGroup();

  constructor(
    protected loginProfileService: LoginProfileService,
    protected loginProfileFormService: LoginProfileFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loginProfile }) => {
      this.loginProfile = loginProfile;
      if (loginProfile) {
        this.updateForm(loginProfile);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const loginProfile = this.loginProfileFormService.getLoginProfile(this.editForm);
    if (loginProfile.id !== null) {
      this.subscribeToSaveResponse(this.loginProfileService.update(loginProfile));
    } else {
      this.subscribeToSaveResponse(this.loginProfileService.create(loginProfile));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoginProfile>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(loginProfile: ILoginProfile): void {
    this.loginProfile = loginProfile;
    this.loginProfileFormService.resetForm(this.editForm, loginProfile);
  }
}
