import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISlide } from '../slide.model';

@Component({
  selector: 'jhi-slide-detail',
  templateUrl: './slide-detail.component.html',
})
export class SlideDetailComponent implements OnInit {
  slide: ISlide | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ slide }) => {
      this.slide = slide;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
