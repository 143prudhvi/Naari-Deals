import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILoginProfile, NewLoginProfile } from '../login-profile.model';

export type PartialUpdateLoginProfile = Partial<ILoginProfile> & Pick<ILoginProfile, 'id'>;

export type EntityResponseType = HttpResponse<ILoginProfile>;
export type EntityArrayResponseType = HttpResponse<ILoginProfile[]>;

@Injectable({ providedIn: 'root' })
export class LoginProfileService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/login-profiles');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(loginProfile: NewLoginProfile): Observable<EntityResponseType> {
    return this.http.post<ILoginProfile>(this.resourceUrl, loginProfile, { observe: 'response' });
  }

  update(loginProfile: ILoginProfile): Observable<EntityResponseType> {
    return this.http.put<ILoginProfile>(`${this.resourceUrl}/${this.getLoginProfileIdentifier(loginProfile)}`, loginProfile, {
      observe: 'response',
    });
  }

  partialUpdate(loginProfile: PartialUpdateLoginProfile): Observable<EntityResponseType> {
    return this.http.patch<ILoginProfile>(`${this.resourceUrl}/${this.getLoginProfileIdentifier(loginProfile)}`, loginProfile, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILoginProfile>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILoginProfile[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLoginProfileIdentifier(loginProfile: Pick<ILoginProfile, 'id'>): number {
    return loginProfile.id;
  }

  compareLoginProfile(o1: Pick<ILoginProfile, 'id'> | null, o2: Pick<ILoginProfile, 'id'> | null): boolean {
    return o1 && o2 ? this.getLoginProfileIdentifier(o1) === this.getLoginProfileIdentifier(o2) : o1 === o2;
  }

  addLoginProfileToCollectionIfMissing<Type extends Pick<ILoginProfile, 'id'>>(
    loginProfileCollection: Type[],
    ...loginProfilesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const loginProfiles: Type[] = loginProfilesToCheck.filter(isPresent);
    if (loginProfiles.length > 0) {
      const loginProfileCollectionIdentifiers = loginProfileCollection.map(
        loginProfileItem => this.getLoginProfileIdentifier(loginProfileItem)!
      );
      const loginProfilesToAdd = loginProfiles.filter(loginProfileItem => {
        const loginProfileIdentifier = this.getLoginProfileIdentifier(loginProfileItem);
        if (loginProfileCollectionIdentifiers.includes(loginProfileIdentifier)) {
          return false;
        }
        loginProfileCollectionIdentifiers.push(loginProfileIdentifier);
        return true;
      });
      return [...loginProfilesToAdd, ...loginProfileCollection];
    }
    return loginProfileCollection;
  }
}
