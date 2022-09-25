import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INotificationType } from '../notification-type.model';
import { NotificationTypeService } from '../service/notification-type.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './notification-type-delete-dialog.component.html',
})
export class NotificationTypeDeleteDialogComponent {
  notificationType?: INotificationType;

  constructor(protected notificationTypeService: NotificationTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.notificationTypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
