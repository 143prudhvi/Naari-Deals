import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDeal } from '../deal.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../deal.test-samples';

import { DealService } from './deal.service';

const requireRestSample: IDeal = {
  ...sampleWithRequiredData,
};

describe('Deal Service', () => {
  let service: DealService;
  let httpMock: HttpTestingController;
  let expectedResult: IDeal | IDeal[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DealService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Deal', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const deal = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(deal).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Deal', () => {
      const deal = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(deal).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Deal', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Deal', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Deal', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDealToCollectionIfMissing', () => {
      it('should add a Deal to an empty array', () => {
        const deal: IDeal = sampleWithRequiredData;
        expectedResult = service.addDealToCollectionIfMissing([], deal);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(deal);
      });

      it('should not add a Deal to an array that contains it', () => {
        const deal: IDeal = sampleWithRequiredData;
        const dealCollection: IDeal[] = [
          {
            ...deal,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDealToCollectionIfMissing(dealCollection, deal);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Deal to an array that doesn't contain it", () => {
        const deal: IDeal = sampleWithRequiredData;
        const dealCollection: IDeal[] = [sampleWithPartialData];
        expectedResult = service.addDealToCollectionIfMissing(dealCollection, deal);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(deal);
      });

      it('should add only unique Deal to an array', () => {
        const dealArray: IDeal[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const dealCollection: IDeal[] = [sampleWithRequiredData];
        expectedResult = service.addDealToCollectionIfMissing(dealCollection, ...dealArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const deal: IDeal = sampleWithRequiredData;
        const deal2: IDeal = sampleWithPartialData;
        expectedResult = service.addDealToCollectionIfMissing([], deal, deal2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(deal);
        expect(expectedResult).toContain(deal2);
      });

      it('should accept null and undefined values', () => {
        const deal: IDeal = sampleWithRequiredData;
        expectedResult = service.addDealToCollectionIfMissing([], null, deal, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(deal);
      });

      it('should return initial array if no Deal is added', () => {
        const dealCollection: IDeal[] = [sampleWithRequiredData];
        expectedResult = service.addDealToCollectionIfMissing(dealCollection, undefined, null);
        expect(expectedResult).toEqual(dealCollection);
      });
    });

    describe('compareDeal', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDeal(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDeal(entity1, entity2);
        const compareResult2 = service.compareDeal(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDeal(entity1, entity2);
        const compareResult2 = service.compareDeal(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDeal(entity1, entity2);
        const compareResult2 = service.compareDeal(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
