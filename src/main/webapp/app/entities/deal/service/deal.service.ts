import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDeal, NewDeal } from '../deal.model';

export type PartialUpdateDeal = Partial<IDeal> & Pick<IDeal, 'id'>;

export type EntityResponseType = HttpResponse<IDeal>;
export type EntityArrayResponseType = HttpResponse<IDeal[]>;

@Injectable({ providedIn: 'root' })
export class DealService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/deals');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(deal: NewDeal): Observable<EntityResponseType> {
    return this.http.post<IDeal>(this.resourceUrl, deal, { observe: 'response' });
  }

  update(deal: IDeal): Observable<EntityResponseType> {
    return this.http.put<IDeal>(`${this.resourceUrl}/${this.getDealIdentifier(deal)}`, deal, { observe: 'response' });
  }

  partialUpdate(deal: PartialUpdateDeal): Observable<EntityResponseType> {
    return this.http.patch<IDeal>(`${this.resourceUrl}/${this.getDealIdentifier(deal)}`, deal, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDeal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDeal[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDealIdentifier(deal: Pick<IDeal, 'id'>): number {
    return deal.id;
  }

  compareDeal(o1: Pick<IDeal, 'id'> | null, o2: Pick<IDeal, 'id'> | null): boolean {
    return o1 && o2 ? this.getDealIdentifier(o1) === this.getDealIdentifier(o2) : o1 === o2;
  }

  addDealToCollectionIfMissing<Type extends Pick<IDeal, 'id'>>(
    dealCollection: Type[],
    ...dealsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const deals: Type[] = dealsToCheck.filter(isPresent);
    if (deals.length > 0) {
      const dealCollectionIdentifiers = dealCollection.map(dealItem => this.getDealIdentifier(dealItem)!);
      const dealsToAdd = deals.filter(dealItem => {
        const dealIdentifier = this.getDealIdentifier(dealItem);
        if (dealCollectionIdentifiers.includes(dealIdentifier)) {
          return false;
        }
        dealCollectionIdentifiers.push(dealIdentifier);
        return true;
      });
      return [...dealsToAdd, ...dealCollection];
    }
    return dealCollection;
  }
}
