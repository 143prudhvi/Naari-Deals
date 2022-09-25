import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMemberType, NewMemberType } from '../member-type.model';

export type PartialUpdateMemberType = Partial<IMemberType> & Pick<IMemberType, 'id'>;

export type EntityResponseType = HttpResponse<IMemberType>;
export type EntityArrayResponseType = HttpResponse<IMemberType[]>;

@Injectable({ providedIn: 'root' })
export class MemberTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/member-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(memberType: NewMemberType): Observable<EntityResponseType> {
    return this.http.post<IMemberType>(this.resourceUrl, memberType, { observe: 'response' });
  }

  update(memberType: IMemberType): Observable<EntityResponseType> {
    return this.http.put<IMemberType>(`${this.resourceUrl}/${this.getMemberTypeIdentifier(memberType)}`, memberType, {
      observe: 'response',
    });
  }

  partialUpdate(memberType: PartialUpdateMemberType): Observable<EntityResponseType> {
    return this.http.patch<IMemberType>(`${this.resourceUrl}/${this.getMemberTypeIdentifier(memberType)}`, memberType, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMemberType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMemberType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMemberTypeIdentifier(memberType: Pick<IMemberType, 'id'>): number {
    return memberType.id;
  }

  compareMemberType(o1: Pick<IMemberType, 'id'> | null, o2: Pick<IMemberType, 'id'> | null): boolean {
    return o1 && o2 ? this.getMemberTypeIdentifier(o1) === this.getMemberTypeIdentifier(o2) : o1 === o2;
  }

  addMemberTypeToCollectionIfMissing<Type extends Pick<IMemberType, 'id'>>(
    memberTypeCollection: Type[],
    ...memberTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const memberTypes: Type[] = memberTypesToCheck.filter(isPresent);
    if (memberTypes.length > 0) {
      const memberTypeCollectionIdentifiers = memberTypeCollection.map(memberTypeItem => this.getMemberTypeIdentifier(memberTypeItem)!);
      const memberTypesToAdd = memberTypes.filter(memberTypeItem => {
        const memberTypeIdentifier = this.getMemberTypeIdentifier(memberTypeItem);
        if (memberTypeCollectionIdentifiers.includes(memberTypeIdentifier)) {
          return false;
        }
        memberTypeCollectionIdentifiers.push(memberTypeIdentifier);
        return true;
      });
      return [...memberTypesToAdd, ...memberTypeCollection];
    }
    return memberTypeCollection;
  }
}
