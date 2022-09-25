import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDealType } from '../deal-type.model';
import { DealTypeService } from '../service/deal-type.service';

@Injectable({ providedIn: 'root' })
export class DealTypeRoutingResolveService implements Resolve<IDealType | null> {
  constructor(protected service: DealTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDealType | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dealType: HttpResponse<IDealType>) => {
          if (dealType.body) {
            return of(dealType.body);
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
