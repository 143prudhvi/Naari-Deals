import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BioProfileComponent } from './list/bio-profile.component';
import { BioProfileDetailComponent } from './detail/bio-profile-detail.component';
import { BioProfileUpdateComponent } from './update/bio-profile-update.component';
import { BioProfileDeleteDialogComponent } from './delete/bio-profile-delete-dialog.component';
import { BioProfileRoutingModule } from './route/bio-profile-routing.module';

@NgModule({
  imports: [SharedModule, BioProfileRoutingModule],
  declarations: [BioProfileComponent, BioProfileDetailComponent, BioProfileUpdateComponent, BioProfileDeleteDialogComponent],
})
export class BioProfileModule {}
