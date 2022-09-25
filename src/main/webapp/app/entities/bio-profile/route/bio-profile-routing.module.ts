import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BioProfileComponent } from '../list/bio-profile.component';
import { BioProfileDetailComponent } from '../detail/bio-profile-detail.component';
import { BioProfileUpdateComponent } from '../update/bio-profile-update.component';
import { BioProfileRoutingResolveService } from './bio-profile-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const bioProfileRoute: Routes = [
  {
    path: '',
    component: BioProfileComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BioProfileDetailComponent,
    resolve: {
      bioProfile: BioProfileRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BioProfileUpdateComponent,
    resolve: {
      bioProfile: BioProfileRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BioProfileUpdateComponent,
    resolve: {
      bioProfile: BioProfileRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bioProfileRoute)],
  exports: [RouterModule],
})
export class BioProfileRoutingModule {}
