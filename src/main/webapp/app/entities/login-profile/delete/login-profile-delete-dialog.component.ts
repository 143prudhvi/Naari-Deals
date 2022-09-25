import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILoginProfile } from '../login-profile.model';
import { LoginProfileService } from '../service/login-profile.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './login-profile-delete-dialog.component.html',
})
export class LoginProfileDeleteDialogComponent {
  loginProfile?: ILoginProfile;

  constructor(protected loginProfileService: LoginProfileService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.loginProfileService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
