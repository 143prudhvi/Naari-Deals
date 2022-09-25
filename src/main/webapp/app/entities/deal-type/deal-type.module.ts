import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DealTypeComponent } from './list/deal-type.component';
import { DealTypeDetailComponent } from './detail/deal-type-detail.component';
import { DealTypeUpdateComponent } from './update/deal-type-update.component';
import { DealTypeDeleteDialogComponent } from './delete/deal-type-delete-dialog.component';
import { DealTypeRoutingModule } from './route/deal-type-routing.module';

@NgModule({
  imports: [SharedModule, DealTypeRoutingModule],
  declarations: [DealTypeComponent, DealTypeDetailComponent, DealTypeUpdateComponent, DealTypeDeleteDialogComponent],
})
export class DealTypeModule {}
