import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CategoryTypeComponent } from '../list/category-type.component';
import { CategoryTypeDetailComponent } from '../detail/category-type-detail.component';
import { CategoryTypeUpdateComponent } from '../update/category-type-update.component';
import { CategoryTypeRoutingResolveService } from './category-type-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const categoryTypeRoute: Routes = [
  {
    path: '',
    component: CategoryTypeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CategoryTypeDetailComponent,
    resolve: {
      categoryType: CategoryTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CategoryTypeUpdateComponent,
    resolve: {
      categoryType: CategoryTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CategoryTypeUpdateComponent,
    resolve: {
      categoryType: CategoryTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(categoryTypeRoute)],
  exports: [RouterModule],
})
export class CategoryTypeRoutingModule {}
