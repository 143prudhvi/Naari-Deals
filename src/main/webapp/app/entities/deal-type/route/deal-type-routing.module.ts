import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DealTypeComponent } from '../list/deal-type.component';
import { DealTypeDetailComponent } from '../detail/deal-type-detail.component';
import { DealTypeUpdateComponent } from '../update/deal-type-update.component';
import { DealTypeRoutingResolveService } from './deal-type-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const dealTypeRoute: Routes = [
  {
    path: '',
    component: DealTypeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DealTypeDetailComponent,
    resolve: {
      dealType: DealTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DealTypeUpdateComponent,
    resolve: {
      dealType: DealTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DealTypeUpdateComponent,
    resolve: {
      dealType: DealTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dealTypeRoute)],
  exports: [RouterModule],
})
export class DealTypeRoutingModule {}
