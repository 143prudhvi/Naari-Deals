import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICategoryType, NewCategoryType } from '../category-type.model';

export type PartialUpdateCategoryType = Partial<ICategoryType> & Pick<ICategoryType, 'id'>;

export type EntityResponseType = HttpResponse<ICategoryType>;
export type EntityArrayResponseType = HttpResponse<ICategoryType[]>;

@Injectable({ providedIn: 'root' })
export class CategoryTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/category-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(categoryType: NewCategoryType): Observable<EntityResponseType> {
    return this.http.post<ICategoryType>(this.resourceUrl, categoryType, { observe: 'response' });
  }

  update(categoryType: ICategoryType): Observable<EntityResponseType> {
    return this.http.put<ICategoryType>(`${this.resourceUrl}/${this.getCategoryTypeIdentifier(categoryType)}`, categoryType, {
      observe: 'response',
    });
  }

  partialUpdate(categoryType: PartialUpdateCategoryType): Observable<EntityResponseType> {
    return this.http.patch<ICategoryType>(`${this.resourceUrl}/${this.getCategoryTypeIdentifier(categoryType)}`, categoryType, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategoryType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategoryType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCategoryTypeIdentifier(categoryType: Pick<ICategoryType, 'id'>): number {
    return categoryType.id;
  }

  compareCategoryType(o1: Pick<ICategoryType, 'id'> | null, o2: Pick<ICategoryType, 'id'> | null): boolean {
    return o1 && o2 ? this.getCategoryTypeIdentifier(o1) === this.getCategoryTypeIdentifier(o2) : o1 === o2;
  }

  addCategoryTypeToCollectionIfMissing<Type extends Pick<ICategoryType, 'id'>>(
    categoryTypeCollection: Type[],
    ...categoryTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const categoryTypes: Type[] = categoryTypesToCheck.filter(isPresent);
    if (categoryTypes.length > 0) {
      const categoryTypeCollectionIdentifiers = categoryTypeCollection.map(
        categoryTypeItem => this.getCategoryTypeIdentifier(categoryTypeItem)!
      );
      const categoryTypesToAdd = categoryTypes.filter(categoryTypeItem => {
        const categoryTypeIdentifier = this.getCategoryTypeIdentifier(categoryTypeItem);
        if (categoryTypeCollectionIdentifiers.includes(categoryTypeIdentifier)) {
          return false;
        }
        categoryTypeCollectionIdentifiers.push(categoryTypeIdentifier);
        return true;
      });
      return [...categoryTypesToAdd, ...categoryTypeCollection];
    }
    return categoryTypeCollection;
  }
}
