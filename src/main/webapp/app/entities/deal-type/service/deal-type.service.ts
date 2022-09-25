import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDealType, NewDealType } from '../deal-type.model';

export type PartialUpdateDealType = Partial<IDealType> & Pick<IDealType, 'id'>;

export type EntityResponseType = HttpResponse<IDealType>;
export type EntityArrayResponseType = HttpResponse<IDealType[]>;

@Injectable({ providedIn: 'root' })
export class DealTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/deal-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dealType: NewDealType): Observable<EntityResponseType> {
    return this.http.post<IDealType>(this.resourceUrl, dealType, { observe: 'response' });
  }

  update(dealType: IDealType): Observable<EntityResponseType> {
    return this.http.put<IDealType>(`${this.resourceUrl}/${this.getDealTypeIdentifier(dealType)}`, dealType, { observe: 'response' });
  }

  partialUpdate(dealType: PartialUpdateDealType): Observable<EntityResponseType> {
    return this.http.patch<IDealType>(`${this.resourceUrl}/${this.getDealTypeIdentifier(dealType)}`, dealType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDealType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDealType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDealTypeIdentifier(dealType: Pick<IDealType, 'id'>): number {
    return dealType.id;
  }

  compareDealType(o1: Pick<IDealType, 'id'> | null, o2: Pick<IDealType, 'id'> | null): boolean {
    return o1 && o2 ? this.getDealTypeIdentifier(o1) === this.getDealTypeIdentifier(o2) : o1 === o2;
  }

  addDealTypeToCollectionIfMissing<Type extends Pick<IDealType, 'id'>>(
    dealTypeCollection: Type[],
    ...dealTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const dealTypes: Type[] = dealTypesToCheck.filter(isPresent);
    if (dealTypes.length > 0) {
      const dealTypeCollectionIdentifiers = dealTypeCollection.map(dealTypeItem => this.getDealTypeIdentifier(dealTypeItem)!);
      const dealTypesToAdd = dealTypes.filter(dealTypeItem => {
        const dealTypeIdentifier = this.getDealTypeIdentifier(dealTypeItem);
        if (dealTypeCollectionIdentifiers.includes(dealTypeIdentifier)) {
          return false;
        }
        dealTypeCollectionIdentifiers.push(dealTypeIdentifier);
        return true;
      });
      return [...dealTypesToAdd, ...dealTypeCollection];
    }
    return dealTypeCollection;
  }
}
