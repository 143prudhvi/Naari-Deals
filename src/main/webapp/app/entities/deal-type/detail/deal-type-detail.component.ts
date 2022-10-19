import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDealType } from '../deal-type.model';

@Component({
  selector: 'jhi-deal-type-detail',
  templateUrl: './deal-type-detail.component.html',
})
export class DealTypeDetailComponent implements OnInit {
  dealType: IDealType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dealType }) => {
      this.dealType = dealType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
