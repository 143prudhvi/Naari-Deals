import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { NotificationTypeFormService, NotificationTypeFormGroup } from './notification-type-form.service';
import { INotificationType } from '../notification-type.model';
import { NotificationTypeService } from '../service/notification-type.service';

@Component({
  selector: 'jhi-notification-type-update',
  templateUrl: './notification-type-update.component.html',
})
export class NotificationTypeUpdateComponent implements OnInit {
  isSaving = false;
  notificationType: INotificationType | null = null;

  editForm: NotificationTypeFormGroup = this.notificationTypeFormService.createNotificationTypeFormGroup();

  constructor(
    protected notificationTypeService: NotificationTypeService,
    protected notificationTypeFormService: NotificationTypeFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notificationType }) => {
      this.notificationType = notificationType;
      if (notificationType) {
        this.updateForm(notificationType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const notificationType = this.notificationTypeFormService.getNotificationType(this.editForm);
    if (notificationType.id !== null) {
      this.subscribeToSaveResponse(this.notificationTypeService.update(notificationType));
    } else {
      this.subscribeToSaveResponse(this.notificationTypeService.create(notificationType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotificationType>>): void {
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

  protected updateForm(notificationType: INotificationType): void {
    this.notificationType = notificationType;
    this.notificationTypeFormService.resetForm(this.editForm, notificationType);
  }
}
