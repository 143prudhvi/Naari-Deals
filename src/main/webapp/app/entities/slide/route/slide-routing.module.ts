import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SlideComponent } from '../list/slide.component';
import { SlideDetailComponent } from '../detail/slide-detail.component';
import { SlideUpdateComponent } from '../update/slide-update.component';
import { SlideRoutingResolveService } from './slide-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const slideRoute: Routes = [
  {
    path: '',
    component: SlideComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SlideDetailComponent,
    resolve: {
      slide: SlideRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SlideUpdateComponent,
    resolve: {
      slide: SlideRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SlideUpdateComponent,
    resolve: {
      slide: SlideRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(slideRoute)],
  exports: [RouterModule],
})
export class SlideRoutingModule {}
