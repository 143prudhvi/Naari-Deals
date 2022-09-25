import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMemberType } from '../member-type.model';
import { MemberTypeService } from '../service/member-type.service';

@Injectable({ providedIn: 'root' })
export class MemberTypeRoutingResolveService implements Resolve<IMemberType | null> {
  constructor(protected service: MemberTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMemberType | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((memberType: HttpResponse<IMemberType>) => {
          if (memberType.body) {
            return of(memberType.body);
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
