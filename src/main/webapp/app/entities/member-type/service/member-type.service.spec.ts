import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMemberType } from '../member-type.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../member-type.test-samples';

import { MemberTypeService } from './member-type.service';

const requireRestSample: IMemberType = {
  ...sampleWithRequiredData,
};

describe('MemberType Service', () => {
  let service: MemberTypeService;
  let httpMock: HttpTestingController;
  let expectedResult: IMemberType | IMemberType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MemberTypeService);
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

    it('should create a MemberType', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const memberType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(memberType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MemberType', () => {
      const memberType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(memberType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MemberType', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MemberType', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MemberType', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMemberTypeToCollectionIfMissing', () => {
      it('should add a MemberType to an empty array', () => {
        const memberType: IMemberType = sampleWithRequiredData;
        expectedResult = service.addMemberTypeToCollectionIfMissing([], memberType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(memberType);
      });

      it('should not add a MemberType to an array that contains it', () => {
        const memberType: IMemberType = sampleWithRequiredData;
        const memberTypeCollection: IMemberType[] = [
          {
            ...memberType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMemberTypeToCollectionIfMissing(memberTypeCollection, memberType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MemberType to an array that doesn't contain it", () => {
        const memberType: IMemberType = sampleWithRequiredData;
        const memberTypeCollection: IMemberType[] = [sampleWithPartialData];
        expectedResult = service.addMemberTypeToCollectionIfMissing(memberTypeCollection, memberType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(memberType);
      });

      it('should add only unique MemberType to an array', () => {
        const memberTypeArray: IMemberType[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const memberTypeCollection: IMemberType[] = [sampleWithRequiredData];
        expectedResult = service.addMemberTypeToCollectionIfMissing(memberTypeCollection, ...memberTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const memberType: IMemberType = sampleWithRequiredData;
        const memberType2: IMemberType = sampleWithPartialData;
        expectedResult = service.addMemberTypeToCollectionIfMissing([], memberType, memberType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(memberType);
        expect(expectedResult).toContain(memberType2);
      });

      it('should accept null and undefined values', () => {
        const memberType: IMemberType = sampleWithRequiredData;
        expectedResult = service.addMemberTypeToCollectionIfMissing([], null, memberType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(memberType);
      });

      it('should return initial array if no MemberType is added', () => {
        const memberTypeCollection: IMemberType[] = [sampleWithRequiredData];
        expectedResult = service.addMemberTypeToCollectionIfMissing(memberTypeCollection, undefined, null);
        expect(expectedResult).toEqual(memberTypeCollection);
      });
    });

    describe('compareMemberType', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMemberType(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMemberType(entity1, entity2);
        const compareResult2 = service.compareMemberType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMemberType(entity1, entity2);
        const compareResult2 = service.compareMemberType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMemberType(entity1, entity2);
        const compareResult2 = service.compareMemberType(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
