import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICategoryType } from '../category-type.model';
import { CategoryTypeService } from '../service/category-type.service';

@Injectable({ providedIn: 'root' })
export class CategoryTypeRoutingResolveService implements Resolve<ICategoryType | null> {
  constructor(protected service: CategoryTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategoryType | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((categoryType: HttpResponse<ICategoryType>) => {
          if (categoryType.body) {
            return of(categoryType.body);
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
