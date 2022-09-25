import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { EmailSubscriptionFormService, EmailSubscriptionFormGroup } from './email-subscription-form.service';
import { IEmailSubscription } from '../email-subscription.model';
import { EmailSubscriptionService } from '../service/email-subscription.service';

@Component({
  selector: 'jhi-email-subscription-update',
  templateUrl: './email-subscription-update.component.html',
})
export class EmailSubscriptionUpdateComponent implements OnInit {
  isSaving = false;
  emailSubscription: IEmailSubscription | null = null;

  editForm: EmailSubscriptionFormGroup = this.emailSubscriptionFormService.createEmailSubscriptionFormGroup();

  constructor(
    protected emailSubscriptionService: EmailSubscriptionService,
    protected emailSubscriptionFormService: EmailSubscriptionFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emailSubscription }) => {
      this.emailSubscription = emailSubscription;
      if (emailSubscription) {
        this.updateForm(emailSubscription);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const emailSubscription = this.emailSubscriptionFormService.getEmailSubscription(this.editForm);
    if (emailSubscription.id !== null) {
      this.subscribeToSaveResponse(this.emailSubscriptionService.update(emailSubscription));
    } else {
      this.subscribeToSaveResponse(this.emailSubscriptionService.create(emailSubscription));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmailSubscription>>): void {
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

  protected updateForm(emailSubscription: IEmailSubscription): void {
    this.emailSubscription = emailSubscription;
    this.emailSubscriptionFormService.resetForm(this.editForm, emailSubscription);
  }
}
