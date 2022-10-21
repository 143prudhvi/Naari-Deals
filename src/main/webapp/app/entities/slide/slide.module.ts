import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SlideComponent } from './list/slide.component';
import { SlideDetailComponent } from './detail/slide-detail.component';
import { SlideUpdateComponent } from './update/slide-update.component';
import { SlideDeleteDialogComponent } from './delete/slide-delete-dialog.component';
import { SlideRoutingModule } from './route/slide-routing.module';

@NgModule({
  imports: [SharedModule, SlideRoutingModule],
  declarations: [SlideComponent, SlideDetailComponent, SlideUpdateComponent, SlideDeleteDialogComponent],
})
export class SlideModule {}
