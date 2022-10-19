import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { DealTypeFormService, DealTypeFormGroup } from './deal-type-form.service';
import { IDealType } from '../deal-type.model';
import { DealTypeService } from '../service/deal-type.service';

@Component({
  selector: 'jhi-deal-type-update',
  templateUrl: './deal-type-update.component.html',
})
export class DealTypeUpdateComponent implements OnInit {
  isSaving = false;
  dealType: IDealType | null = null;

  editForm: DealTypeFormGroup = this.dealTypeFormService.createDealTypeFormGroup();

  constructor(
    protected dealTypeService: DealTypeService,
    protected dealTypeFormService: DealTypeFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dealType }) => {
      this.dealType = dealType;
      if (dealType) {
        this.updateForm(dealType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dealType = this.dealTypeFormService.getDealType(this.editForm);
    if (dealType.id !== null) {
      this.subscribeToSaveResponse(this.dealTypeService.update(dealType));
    } else {
      this.subscribeToSaveResponse(this.dealTypeService.create(dealType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDealType>>): void {
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

  protected updateForm(dealType: IDealType): void {
    this.dealType = dealType;
    this.dealTypeFormService.resetForm(this.editForm, dealType);
  }
}
