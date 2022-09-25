import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EmailSubscriptionComponent } from '../list/email-subscription.component';
import { EmailSubscriptionDetailComponent } from '../detail/email-subscription-detail.component';
import { EmailSubscriptionUpdateComponent } from '../update/email-subscription-update.component';
import { EmailSubscriptionRoutingResolveService } from './email-subscription-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const emailSubscriptionRoute: Routes = [
  {
    path: '',
    component: EmailSubscriptionComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmailSubscriptionDetailComponent,
    resolve: {
      emailSubscription: EmailSubscriptionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmailSubscriptionUpdateComponent,
    resolve: {
      emailSubscription: EmailSubscriptionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmailSubscriptionUpdateComponent,
    resolve: {
      emailSubscription: EmailSubscriptionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(emailSubscriptionRoute)],
  exports: [RouterModule],
})
export class EmailSubscriptionRoutingModule {}
