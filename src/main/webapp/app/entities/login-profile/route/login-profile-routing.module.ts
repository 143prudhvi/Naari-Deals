import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LoginProfileComponent } from '../list/login-profile.component';
import { LoginProfileDetailComponent } from '../detail/login-profile-detail.component';
import { LoginProfileUpdateComponent } from '../update/login-profile-update.component';
import { LoginProfileRoutingResolveService } from './login-profile-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const loginProfileRoute: Routes = [
  {
    path: '',
    component: LoginProfileComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LoginProfileDetailComponent,
    resolve: {
      loginProfile: LoginProfileRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LoginProfileUpdateComponent,
    resolve: {
      loginProfile: LoginProfileRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LoginProfileUpdateComponent,
    resolve: {
      loginProfile: LoginProfileRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(loginProfileRoute)],
  exports: [RouterModule],
})
export class LoginProfileRoutingModule {}
