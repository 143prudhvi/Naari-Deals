import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDealType } from '../deal-type.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../deal-type.test-samples';

import { DealTypeService } from './deal-type.service';

const requireRestSample: IDealType = {
  ...sampleWithRequiredData,
};

describe('DealType Service', () => {
  let service: DealTypeService;
  let httpMock: HttpTestingController;
  let expectedResult: IDealType | IDealType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DealTypeService);
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

    it('should create a DealType', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const dealType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(dealType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DealType', () => {
      const dealType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(dealType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DealType', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DealType', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DealType', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDealTypeToCollectionIfMissing', () => {
      it('should add a DealType to an empty array', () => {
        const dealType: IDealType = sampleWithRequiredData;
        expectedResult = service.addDealTypeToCollectionIfMissing([], dealType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dealType);
      });

      it('should not add a DealType to an array that contains it', () => {
        const dealType: IDealType = sampleWithRequiredData;
        const dealTypeCollection: IDealType[] = [
          {
            ...dealType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDealTypeToCollectionIfMissing(dealTypeCollection, dealType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DealType to an array that doesn't contain it", () => {
        const dealType: IDealType = sampleWithRequiredData;
        const dealTypeCollection: IDealType[] = [sampleWithPartialData];
        expectedResult = service.addDealTypeToCollectionIfMissing(dealTypeCollection, dealType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dealType);
      });

      it('should add only unique DealType to an array', () => {
        const dealTypeArray: IDealType[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const dealTypeCollection: IDealType[] = [sampleWithRequiredData];
        expectedResult = service.addDealTypeToCollectionIfMissing(dealTypeCollection, ...dealTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dealType: IDealType = sampleWithRequiredData;
        const dealType2: IDealType = sampleWithPartialData;
        expectedResult = service.addDealTypeToCollectionIfMissing([], dealType, dealType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dealType);
        expect(expectedResult).toContain(dealType2);
      });

      it('should accept null and undefined values', () => {
        const dealType: IDealType = sampleWithRequiredData;
        expectedResult = service.addDealTypeToCollectionIfMissing([], null, dealType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dealType);
      });

      it('should return initial array if no DealType is added', () => {
        const dealTypeCollection: IDealType[] = [sampleWithRequiredData];
        expectedResult = service.addDealTypeToCollectionIfMissing(dealTypeCollection, undefined, null);
        expect(expectedResult).toEqual(dealTypeCollection);
      });
    });

    describe('compareDealType', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDealType(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDealType(entity1, entity2);
        const compareResult2 = service.compareDealType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDealType(entity1, entity2);
        const compareResult2 = service.compareDealType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDealType(entity1, entity2);
        const compareResult2 = service.compareDealType(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
