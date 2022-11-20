import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { SlideFormService, SlideFormGroup } from './slide-form.service';
import { ISlide } from '../slide.model';
import { SlideService } from '../service/slide.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-slide-update',
  templateUrl: './slide-update.component.html',
})
export class SlideUpdateComponent implements OnInit {
  isSaving = false;
  slide: ISlide | null = null;

  editForm: SlideFormGroup = this.slideFormService.createSlideFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected slideService: SlideService,
    protected slideFormService: SlideFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ slide }) => {
      this.slide = slide;
      if (slide) {
        this.updateForm(slide);
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
    const slide = this.slideFormService.getSlide(this.editForm);
    if (slide.id !== null) {
      this.subscribeToSaveResponse(this.slideService.update(slide));
    } else {
      this.subscribeToSaveResponse(this.slideService.create(slide));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISlide>>): void {
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

  protected updateForm(slide: ISlide): void {
    this.slide = slide;
    this.slideFormService.resetForm(this.editForm, slide);
  }
}
