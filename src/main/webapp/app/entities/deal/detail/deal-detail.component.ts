import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeal } from '../deal.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-deal-detail',
  templateUrl: './deal-detail.component.html',
})
export class DealDetailComponent implements OnInit {
  deal: IDeal | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deal }) => {
      this.deal = deal;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
