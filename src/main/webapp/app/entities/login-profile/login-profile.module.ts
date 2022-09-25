import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LoginProfileComponent } from './list/login-profile.component';
import { LoginProfileDetailComponent } from './detail/login-profile-detail.component';
import { LoginProfileUpdateComponent } from './update/login-profile-update.component';
import { LoginProfileDeleteDialogComponent } from './delete/login-profile-delete-dialog.component';
import { LoginProfileRoutingModule } from './route/login-profile-routing.module';

@NgModule({
  imports: [SharedModule, LoginProfileRoutingModule],
  declarations: [LoginProfileComponent, LoginProfileDetailComponent, LoginProfileUpdateComponent, LoginProfileDeleteDialogComponent],
})
export class LoginProfileModule {}
