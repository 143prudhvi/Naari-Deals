import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmailSubscription } from '../email-subscription.model';

@Component({
  selector: 'jhi-email-subscription-detail',
  templateUrl: './email-subscription-detail.component.html',
})
export class EmailSubscriptionDetailComponent implements OnInit {
  emailSubscription: IEmailSubscription | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emailSubscription }) => {
      this.emailSubscription = emailSubscription;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
