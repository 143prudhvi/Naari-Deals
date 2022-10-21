import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISlide } from '../slide.model';
import { SlideService } from '../service/slide.service';

@Injectable({ providedIn: 'root' })
export class SlideRoutingResolveService implements Resolve<ISlide | null> {
  constructor(protected service: SlideService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISlide | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((slide: HttpResponse<ISlide>) => {
          if (slide.body) {
            return of(slide.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
