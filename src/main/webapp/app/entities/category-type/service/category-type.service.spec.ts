import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICategoryType } from '../category-type.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../category-type.test-samples';

import { CategoryTypeService } from './category-type.service';

const requireRestSample: ICategoryType = {
  ...sampleWithRequiredData,
};

describe('CategoryType Service', () => {
  let service: CategoryTypeService;
  let httpMock: HttpTestingController;
  let expectedResult: ICategoryType | ICategoryType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CategoryTypeService);
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

    it('should create a CategoryType', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const categoryType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(categoryType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CategoryType', () => {
      const categoryType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(categoryType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CategoryType', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CategoryType', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CategoryType', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCategoryTypeToCollectionIfMissing', () => {
      it('should add a CategoryType to an empty array', () => {
        const categoryType: ICategoryType = sampleWithRequiredData;
        expectedResult = service.addCategoryTypeToCollectionIfMissing([], categoryType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categoryType);
      });

      it('should not add a CategoryType to an array that contains it', () => {
        const categoryType: ICategoryType = sampleWithRequiredData;
        const categoryTypeCollection: ICategoryType[] = [
          {
            ...categoryType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCategoryTypeToCollectionIfMissing(categoryTypeCollection, categoryType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CategoryType to an array that doesn't contain it", () => {
        const categoryType: ICategoryType = sampleWithRequiredData;
        const categoryTypeCollection: ICategoryType[] = [sampleWithPartialData];
        expectedResult = service.addCategoryTypeToCollectionIfMissing(categoryTypeCollection, categoryType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categoryType);
      });

      it('should add only unique CategoryType to an array', () => {
        const categoryTypeArray: ICategoryType[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const categoryTypeCollection: ICategoryType[] = [sampleWithRequiredData];
        expectedResult = service.addCategoryTypeToCollectionIfMissing(categoryTypeCollection, ...categoryTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const categoryType: ICategoryType = sampleWithRequiredData;
        const categoryType2: ICategoryType = sampleWithPartialData;
        expectedResult = service.addCategoryTypeToCollectionIfMissing([], categoryType, categoryType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categoryType);
        expect(expectedResult).toContain(categoryType2);
      });

      it('should accept null and undefined values', () => {
        const categoryType: ICategoryType = sampleWithRequiredData;
        expectedResult = service.addCategoryTypeToCollectionIfMissing([], null, categoryType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categoryType);
      });

      it('should return initial array if no CategoryType is added', () => {
        const categoryTypeCollection: ICategoryType[] = [sampleWithRequiredData];
        expectedResult = service.addCategoryTypeToCollectionIfMissing(categoryTypeCollection, undefined, null);
        expect(expectedResult).toEqual(categoryTypeCollection);
      });
    });

    describe('compareCategoryType', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCategoryType(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCategoryType(entity1, entity2);
        const compareResult2 = service.compareCategoryType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCategoryType(entity1, entity2);
        const compareResult2 = service.compareCategoryType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCategoryType(entity1, entity2);
        const compareResult2 = service.compareCategoryType(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
