import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EmailSubscriptionComponent } from './list/email-subscription.component';
import { EmailSubscriptionDetailComponent } from './detail/email-subscription-detail.component';
import { EmailSubscriptionUpdateComponent } from './update/email-subscription-update.component';
import { EmailSubscriptionDeleteDialogComponent } from './delete/email-subscription-delete-dialog.component';
import { EmailSubscriptionRoutingModule } from './route/email-subscription-routing.module';

@NgModule({
  imports: [SharedModule, EmailSubscriptionRoutingModule],
  declarations: [
    EmailSubscriptionComponent,
    EmailSubscriptionDetailComponent,
    EmailSubscriptionUpdateComponent,
    EmailSubscriptionDeleteDialogComponent,
  ],
})
export class EmailSubscriptionModule {}
