import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CategoryTypeComponent } from './list/category-type.component';
import { CategoryTypeDetailComponent } from './detail/category-type-detail.component';
import { CategoryTypeUpdateComponent } from './update/category-type-update.component';
import { CategoryTypeDeleteDialogComponent } from './delete/category-type-delete-dialog.component';
import { CategoryTypeRoutingModule } from './route/category-type-routing.module';

@NgModule({
  imports: [SharedModule, CategoryTypeRoutingModule],
  declarations: [CategoryTypeComponent, CategoryTypeDetailComponent, CategoryTypeUpdateComponent, CategoryTypeDeleteDialogComponent],
})
export class CategoryTypeModule {}
