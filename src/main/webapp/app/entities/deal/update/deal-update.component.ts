import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { DealFormService, DealFormGroup } from './deal-form.service';
import { IDeal } from '../deal.model';
import { DealService } from '../service/deal.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-deal-update',
  templateUrl: './deal-update.component.html',
})
export class DealUpdateComponent implements OnInit {
  isSaving = false;
  deal: IDeal | null = null;

  editForm: DealFormGroup = this.dealFormService.createDealFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected dealService: DealService,
    protected dealFormService: DealFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deal }) => {
      this.deal = deal;
      if (deal) {
        this.updateForm(deal);
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
    const deal = this.dealFormService.getDeal(this.editForm);
    if (deal.id !== null) {
      this.subscribeToSaveResponse(this.dealService.update(deal));
    } else {
      this.subscribeToSaveResponse(this.dealService.create(deal));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeal>>): void {
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

  protected updateForm(deal: IDeal): void {
    this.deal = deal;
    this.dealFormService.resetForm(this.editForm, deal);
  }
}
