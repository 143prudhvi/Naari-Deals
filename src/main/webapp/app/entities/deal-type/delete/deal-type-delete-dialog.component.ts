import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDealType } from '../deal-type.model';
import { DealTypeService } from '../service/deal-type.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './deal-type-delete-dialog.component.html',
})
export class DealTypeDeleteDialogComponent {
  dealType?: IDealType;

  constructor(protected dealTypeService: DealTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dealTypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
