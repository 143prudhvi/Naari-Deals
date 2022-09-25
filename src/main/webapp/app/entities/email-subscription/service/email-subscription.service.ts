import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmailSubscription, NewEmailSubscription } from '../email-subscription.model';

export type PartialUpdateEmailSubscription = Partial<IEmailSubscription> & Pick<IEmailSubscription, 'id'>;

export type EntityResponseType = HttpResponse<IEmailSubscription>;
export type EntityArrayResponseType = HttpResponse<IEmailSubscription[]>;

@Injectable({ providedIn: 'root' })
export class EmailSubscriptionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/email-subscriptions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(emailSubscription: NewEmailSubscription): Observable<EntityResponseType> {
    return this.http.post<IEmailSubscription>(this.resourceUrl, emailSubscription, { observe: 'response' });
  }

  update(emailSubscription: IEmailSubscription): Observable<EntityResponseType> {
    return this.http.put<IEmailSubscription>(
      `${this.resourceUrl}/${this.getEmailSubscriptionIdentifier(emailSubscription)}`,
      emailSubscription,
      { observe: 'response' }
    );
  }

  partialUpdate(emailSubscription: PartialUpdateEmailSubscription): Observable<EntityResponseType> {
    return this.http.patch<IEmailSubscription>(
      `${this.resourceUrl}/${this.getEmailSubscriptionIdentifier(emailSubscription)}`,
      emailSubscription,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmailSubscription>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmailSubscription[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEmailSubscriptionIdentifier(emailSubscription: Pick<IEmailSubscription, 'id'>): number {
    return emailSubscription.id;
  }

  compareEmailSubscription(o1: Pick<IEmailSubscription, 'id'> | null, o2: Pick<IEmailSubscription, 'id'> | null): boolean {
    return o1 && o2 ? this.getEmailSubscriptionIdentifier(o1) === this.getEmailSubscriptionIdentifier(o2) : o1 === o2;
  }

  addEmailSubscriptionToCollectionIfMissing<Type extends Pick<IEmailSubscription, 'id'>>(
    emailSubscriptionCollection: Type[],
    ...emailSubscriptionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const emailSubscriptions: Type[] = emailSubscriptionsToCheck.filter(isPresent);
    if (emailSubscriptions.length > 0) {
      const emailSubscriptionCollectionIdentifiers = emailSubscriptionCollection.map(
        emailSubscriptionItem => this.getEmailSubscriptionIdentifier(emailSubscriptionItem)!
      );
      const emailSubscriptionsToAdd = emailSubscriptions.filter(emailSubscriptionItem => {
        const emailSubscriptionIdentifier = this.getEmailSubscriptionIdentifier(emailSubscriptionItem);
        if (emailSubscriptionCollectionIdentifiers.includes(emailSubscriptionIdentifier)) {
          return false;
        }
        emailSubscriptionCollectionIdentifiers.push(emailSubscriptionIdentifier);
        return true;
      });
      return [...emailSubscriptionsToAdd, ...emailSubscriptionCollection];
    }
    return emailSubscriptionCollection;
  }
}
