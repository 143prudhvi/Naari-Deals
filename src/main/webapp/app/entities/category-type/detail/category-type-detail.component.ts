import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategoryType } from '../category-type.model';

@Component({
  selector: 'jhi-category-type-detail',
  templateUrl: './category-type-detail.component.html',
})
export class CategoryTypeDetailComponent implements OnInit {
  categoryType: ICategoryType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoryType }) => {
      this.categoryType = categoryType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
