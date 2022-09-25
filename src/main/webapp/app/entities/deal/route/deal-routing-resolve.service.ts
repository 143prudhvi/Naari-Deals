import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDeal } from '../deal.model';
import { DealService } from '../service/deal.service';

@Injectable({ providedIn: 'root' })
export class DealRoutingResolveService implements Resolve<IDeal | null> {
  constructor(protected service: DealService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDeal | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((deal: HttpResponse<IDeal>) => {
          if (deal.body) {
            return of(deal.body);
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
