import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MemberTypeComponent } from '../list/member-type.component';
import { MemberTypeDetailComponent } from '../detail/member-type-detail.component';
import { MemberTypeUpdateComponent } from '../update/member-type-update.component';
import { MemberTypeRoutingResolveService } from './member-type-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const memberTypeRoute: Routes = [
  {
    path: '',
    component: MemberTypeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MemberTypeDetailComponent,
    resolve: {
      memberType: MemberTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MemberTypeUpdateComponent,
    resolve: {
      memberType: MemberTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MemberTypeUpdateComponent,
    resolve: {
      memberType: MemberTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(memberTypeRoute)],
  exports: [RouterModule],
})
export class MemberTypeRoutingModule {}
