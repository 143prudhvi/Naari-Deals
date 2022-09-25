import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { MemberTypeFormService, MemberTypeFormGroup } from './member-type-form.service';
import { IMemberType } from '../member-type.model';
import { MemberTypeService } from '../service/member-type.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-member-type-update',
  templateUrl: './member-type-update.component.html',
})
export class MemberTypeUpdateComponent implements OnInit {
  isSaving = false;
  memberType: IMemberType | null = null;

  editForm: MemberTypeFormGroup = this.memberTypeFormService.createMemberTypeFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected memberTypeService: MemberTypeService,
    protected memberTypeFormService: MemberTypeFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ memberType }) => {
      this.memberType = memberType;
      if (memberType) {
        this.updateForm(memberType);
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
    const memberType = this.memberTypeFormService.getMemberType(this.editForm);
    if (memberType.id !== null) {
      this.subscribeToSaveResponse(this.memberTypeService.update(memberType));
    } else {
      this.subscribeToSaveResponse(this.memberTypeService.create(memberType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMemberType>>): void {
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

  protected updateForm(memberType: IMemberType): void {
    this.memberType = memberType;
    this.memberTypeFormService.resetForm(this.editForm, memberType);
  }
}
