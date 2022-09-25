import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBioProfile, NewBioProfile } from '../bio-profile.model';

export type PartialUpdateBioProfile = Partial<IBioProfile> & Pick<IBioProfile, 'id'>;

export type EntityResponseType = HttpResponse<IBioProfile>;
export type EntityArrayResponseType = HttpResponse<IBioProfile[]>;

@Injectable({ providedIn: 'root' })
export class BioProfileService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bio-profiles');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(bioProfile: NewBioProfile): Observable<EntityResponseType> {
    return this.http.post<IBioProfile>(this.resourceUrl, bioProfile, { observe: 'response' });
  }

  update(bioProfile: IBioProfile): Observable<EntityResponseType> {
    return this.http.put<IBioProfile>(`${this.resourceUrl}/${this.getBioProfileIdentifier(bioProfile)}`, bioProfile, {
      observe: 'response',
    });
  }

  partialUpdate(bioProfile: PartialUpdateBioProfile): Observable<EntityResponseType> {
    return this.http.patch<IBioProfile>(`${this.resourceUrl}/${this.getBioProfileIdentifier(bioProfile)}`, bioProfile, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBioProfile>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBioProfile[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBioProfileIdentifier(bioProfile: Pick<IBioProfile, 'id'>): number {
    return bioProfile.id;
  }

  compareBioProfile(o1: Pick<IBioProfile, 'id'> | null, o2: Pick<IBioProfile, 'id'> | null): boolean {
    return o1 && o2 ? this.getBioProfileIdentifier(o1) === this.getBioProfileIdentifier(o2) : o1 === o2;
  }

  addBioProfileToCollectionIfMissing<Type extends Pick<IBioProfile, 'id'>>(
    bioProfileCollection: Type[],
    ...bioProfilesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const bioProfiles: Type[] = bioProfilesToCheck.filter(isPresent);
    if (bioProfiles.length > 0) {
      const bioProfileCollectionIdentifiers = bioProfileCollection.map(bioProfileItem => this.getBioProfileIdentifier(bioProfileItem)!);
      const bioProfilesToAdd = bioProfiles.filter(bioProfileItem => {
        const bioProfileIdentifier = this.getBioProfileIdentifier(bioProfileItem);
        if (bioProfileCollectionIdentifiers.includes(bioProfileIdentifier)) {
          return false;
        }
        bioProfileCollectionIdentifiers.push(bioProfileIdentifier);
        return true;
      });
      return [...bioProfilesToAdd, ...bioProfileCollection];
    }
    return bioProfileCollection;
  }
}
