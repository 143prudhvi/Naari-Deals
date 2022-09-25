import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILoginProfile } from '../login-profile.model';
import { LoginProfileService } from '../service/login-profile.service';

@Injectable({ providedIn: 'root' })
export class LoginProfileRoutingResolveService implements Resolve<ILoginProfile | null> {
  constructor(protected service: LoginProfileService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILoginProfile | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((loginProfile: HttpResponse<ILoginProfile>) => {
          if (loginProfile.body) {
            return of(loginProfile.body);
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
