import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CategoryTypeFormService, CategoryTypeFormGroup } from './category-type-form.service';
import { ICategoryType } from '../category-type.model';
import { CategoryTypeService } from '../service/category-type.service';

@Component({
  selector: 'jhi-category-type-update',
  templateUrl: './category-type-update.component.html',
})
export class CategoryTypeUpdateComponent implements OnInit {
  isSaving = false;
  categoryType: ICategoryType | null = null;

  editForm: CategoryTypeFormGroup = this.categoryTypeFormService.createCategoryTypeFormGroup();

  constructor(
    protected categoryTypeService: CategoryTypeService,
    protected categoryTypeFormService: CategoryTypeFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoryType }) => {
      this.categoryType = categoryType;
      if (categoryType) {
        this.updateForm(categoryType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categoryType = this.categoryTypeFormService.getCategoryType(this.editForm);
    if (categoryType.id !== null) {
      this.subscribeToSaveResponse(this.categoryTypeService.update(categoryType));
    } else {
      this.subscribeToSaveResponse(this.categoryTypeService.create(categoryType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoryType>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(categoryType: ICategoryType): void {
    this.categoryType = categoryType;
    this.categoryTypeFormService.resetForm(this.editForm, categoryType);
  }
}
