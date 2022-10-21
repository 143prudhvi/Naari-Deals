import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISlide, NewSlide } from '../slide.model';

export type PartialUpdateSlide = Partial<ISlide> & Pick<ISlide, 'id'>;

export type EntityResponseType = HttpResponse<ISlide>;
export type EntityArrayResponseType = HttpResponse<ISlide[]>;

@Injectable({ providedIn: 'root' })
export class SlideService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/slides');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(slide: NewSlide): Observable<EntityResponseType> {
    return this.http.post<ISlide>(this.resourceUrl, slide, { observe: 'response' });
  }

  update(slide: ISlide): Observable<EntityResponseType> {
    return this.http.put<ISlide>(`${this.resourceUrl}/${this.getSlideIdentifier(slide)}`, slide, { observe: 'response' });
  }

  partialUpdate(slide: PartialUpdateSlide): Observable<EntityResponseType> {
    return this.http.patch<ISlide>(`${this.resourceUrl}/${this.getSlideIdentifier(slide)}`, slide, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISlide>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISlide[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSlideIdentifier(slide: Pick<ISlide, 'id'>): number {
    return slide.id;
  }

  compareSlide(o1: Pick<ISlide, 'id'> | null, o2: Pick<ISlide, 'id'> | null): boolean {
    return o1 && o2 ? this.getSlideIdentifier(o1) === this.getSlideIdentifier(o2) : o1 === o2;
  }

  addSlideToCollectionIfMissing<Type extends Pick<ISlide, 'id'>>(
    slideCollection: Type[],
    ...slidesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const slides: Type[] = slidesToCheck.filter(isPresent);
    if (slides.length > 0) {
      const slideCollectionIdentifiers = slideCollection.map(slideItem => this.getSlideIdentifier(slideItem)!);
      const slidesToAdd = slides.filter(slideItem => {
        const slideIdentifier = this.getSlideIdentifier(slideItem);
        if (slideCollectionIdentifiers.includes(slideIdentifier)) {
          return false;
        }
        slideCollectionIdentifiers.push(slideIdentifier);
        return true;
      });
      return [...slidesToAdd, ...slideCollection];
    }
    return slideCollection;
  }
}
