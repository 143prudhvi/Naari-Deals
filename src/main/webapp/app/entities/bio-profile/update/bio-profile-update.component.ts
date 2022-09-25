import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { BioProfileFormService, BioProfileFormGroup } from './bio-profile-form.service';
import { IBioProfile } from '../bio-profile.model';
import { BioProfileService } from '../service/bio-profile.service';

@Component({
  selector: 'jhi-bio-profile-update',
  templateUrl: './bio-profile-update.component.html',
})
export class BioProfileUpdateComponent implements OnInit {
  isSaving = false;
  bioProfile: IBioProfile | null = null;

  editForm: BioProfileFormGroup = this.bioProfileFormService.createBioProfileFormGroup();

  constructor(
    protected bioProfileService: BioProfileService,
    protected bioProfileFormService: BioProfileFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bioProfile }) => {
      this.bioProfile = bioProfile;
      if (bioProfile) {
        this.updateForm(bioProfile);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bioProfile = this.bioProfileFormService.getBioProfile(this.editForm);
    if (bioProfile.id !== null) {
      this.subscribeToSaveResponse(this.bioProfileService.update(bioProfile));
    } else {
      this.subscribeToSaveResponse(this.bioProfileService.create(bioProfile));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBioProfile>>): void {
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

  protected updateForm(bioProfile: IBioProfile): void {
    this.bioProfile = bioProfile;
    this.bioProfileFormService.resetForm(this.editForm, bioProfile);
  }
}
