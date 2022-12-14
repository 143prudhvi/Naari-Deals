import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISlide } from '../slide.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-slide-detail',
  templateUrl: './slide-detail.component.html',
})
export class SlideDetailComponent implements OnInit {
  slide: ISlide | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ slide }) => {
      this.slide = slide;
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
