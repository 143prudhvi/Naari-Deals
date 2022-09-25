import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { MerchantFormService, MerchantFormGroup } from './merchant-form.service';
import { IMerchant } from '../merchant.model';
import { MerchantService } from '../service/merchant.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-merchant-update',
  templateUrl: './merchant-update.component.html',
})
export class MerchantUpdateComponent implements OnInit {
  isSaving = false;
  merchant: IMerchant | null = null;

  editForm: MerchantFormGroup = this.merchantFormService.createMerchantFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected merchantService: MerchantService,
    protected merchantFormService: MerchantFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ merchant }) => {
      this.merchant = merchant;
      if (merchant) {
        this.updateForm(merchant);
      }
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('naariDealsApp.error', { message: err.message })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const merchant = this.merchantFormService.getMerchant(this.editForm);
    if (merchant.id !== null) {
      this.subscribeToSaveResponse(this.merchantService.update(merchant));
    } else {
      this.subscribeToSaveResponse(this.merchantService.create(merchant));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMerchant>>): void {
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

  protected updateForm(merchant: IMerchant): void {
    this.merchant = merchant;
    this.merchantFormService.resetForm(this.editForm, merchant);
  }
}
