import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INotificationType, NewNotificationType } from '../notification-type.model';

export type PartialUpdateNotificationType = Partial<INotificationType> & Pick<INotificationType, 'id'>;

export type EntityResponseType = HttpResponse<INotificationType>;
export type EntityArrayResponseType = HttpResponse<INotificationType[]>;

@Injectable({ providedIn: 'root' })
export class NotificationTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/notification-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(notificationType: NewNotificationType): Observable<EntityResponseType> {
    return this.http.post<INotificationType>(this.resourceUrl, notificationType, { observe: 'response' });
  }

  update(notificationType: INotificationType): Observable<EntityResponseType> {
    return this.http.put<INotificationType>(
      `${this.resourceUrl}/${this.getNotificationTypeIdentifier(notificationType)}`,
      notificationType,
      { observe: 'response' }
    );
  }

  partialUpdate(notificationType: PartialUpdateNotificationType): Observable<EntityResponseType> {
    return this.http.patch<INotificationType>(
      `${this.resourceUrl}/${this.getNotificationTypeIdentifier(notificationType)}`,
      notificationType,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INotificationType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INotificationType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getNotificationTypeIdentifier(notificationType: Pick<INotificationType, 'id'>): number {
    return notificationType.id;
  }

  compareNotificationType(o1: Pick<INotificationType, 'id'> | null, o2: Pick<INotificationType, 'id'> | null): boolean {
    return o1 && o2 ? this.getNotificationTypeIdentifier(o1) === this.getNotificationTypeIdentifier(o2) : o1 === o2;
  }

  addNotificationTypeToCollectionIfMissing<Type extends Pick<INotificationType, 'id'>>(
    notificationTypeCollection: Type[],
    ...notificationTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const notificationTypes: Type[] = notificationTypesToCheck.filter(isPresent);
    if (notificationTypes.length > 0) {
      const notificationTypeCollectionIdentifiers = notificationTypeCollection.map(
        notificationTypeItem => this.getNotificationTypeIdentifier(notificationTypeItem)!
      );
      const notificationTypesToAdd = notificationTypes.filter(notificationTypeItem => {
        const notificationTypeIdentifier = this.getNotificationTypeIdentifier(notificationTypeItem);
        if (notificationTypeCollectionIdentifiers.includes(notificationTypeIdentifier)) {
          return false;
        }
        notificationTypeCollectionIdentifiers.push(notificationTypeIdentifier);
        return true;
      });
      return [...notificationTypesToAdd, ...notificationTypeCollection];
    }
    return notificationTypeCollection;
  }
}
