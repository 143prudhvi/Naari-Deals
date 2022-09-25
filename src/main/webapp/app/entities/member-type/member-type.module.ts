import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MemberTypeComponent } from './list/member-type.component';
import { MemberTypeDetailComponent } from './detail/member-type-detail.component';
import { MemberTypeUpdateComponent } from './update/member-type-update.component';
import { MemberTypeDeleteDialogComponent } from './delete/member-type-delete-dialog.component';
import { MemberTypeRoutingModule } from './route/member-type-routing.module';

@NgModule({
  imports: [SharedModule, MemberTypeRoutingModule],
  declarations: [MemberTypeComponent, MemberTypeDetailComponent, MemberTypeUpdateComponent, MemberTypeDeleteDialogComponent],
})
export class MemberTypeModule {}
