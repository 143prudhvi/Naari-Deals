import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICategoryType } from '../category-type.model';
import { CategoryTypeService } from '../service/category-type.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './category-type-delete-dialog.component.html',
})
export class CategoryTypeDeleteDialogComponent {
  categoryType?: ICategoryType;

  constructor(protected categoryTypeService: CategoryTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categoryTypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
