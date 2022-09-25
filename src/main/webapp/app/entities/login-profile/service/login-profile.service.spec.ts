import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILoginProfile } from '../login-profile.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../login-profile.test-samples';

import { LoginProfileService } from './login-profile.service';

const requireRestSample: ILoginProfile = {
  ...sampleWithRequiredData,
};

describe('LoginProfile Service', () => {
  let service: LoginProfileService;
  let httpMock: HttpTestingController;
  let expectedResult: ILoginProfile | ILoginProfile[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LoginProfileService);
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

    it('should create a LoginProfile', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const loginProfile = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(loginProfile).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LoginProfile', () => {
      const loginProfile = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(loginProfile).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LoginProfile', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LoginProfile', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LoginProfile', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLoginProfileToCollectionIfMissing', () => {
      it('should add a LoginProfile to an empty array', () => {
        const loginProfile: ILoginProfile = sampleWithRequiredData;
        expectedResult = service.addLoginProfileToCollectionIfMissing([], loginProfile);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(loginProfile);
      });

      it('should not add a LoginProfile to an array that contains it', () => {
        const loginProfile: ILoginProfile = sampleWithRequiredData;
        const loginProfileCollection: ILoginProfile[] = [
          {
            ...loginProfile,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLoginProfileToCollectionIfMissing(loginProfileCollection, loginProfile);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LoginProfile to an array that doesn't contain it", () => {
        const loginProfile: ILoginProfile = sampleWithRequiredData;
        const loginProfileCollection: ILoginProfile[] = [sampleWithPartialData];
        expectedResult = service.addLoginProfileToCollectionIfMissing(loginProfileCollection, loginProfile);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(loginProfile);
      });

      it('should add only unique LoginProfile to an array', () => {
        const loginProfileArray: ILoginProfile[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const loginProfileCollection: ILoginProfile[] = [sampleWithRequiredData];
        expectedResult = service.addLoginProfileToCollectionIfMissing(loginProfileCollection, ...loginProfileArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const loginProfile: ILoginProfile = sampleWithRequiredData;
        const loginProfile2: ILoginProfile = sampleWithPartialData;
        expectedResult = service.addLoginProfileToCollectionIfMissing([], loginProfile, loginProfile2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(loginProfile);
        expect(expectedResult).toContain(loginProfile2);
      });

      it('should accept null and undefined values', () => {
        const loginProfile: ILoginProfile = sampleWithRequiredData;
        expectedResult = service.addLoginProfileToCollectionIfMissing([], null, loginProfile, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(loginProfile);
      });

      it('should return initial array if no LoginProfile is added', () => {
        const loginProfileCollection: ILoginProfile[] = [sampleWithRequiredData];
        expectedResult = service.addLoginProfileToCollectionIfMissing(loginProfileCollection, undefined, null);
        expect(expectedResult).toEqual(loginProfileCollection);
      });
    });

    describe('compareLoginProfile', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLoginProfile(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLoginProfile(entity1, entity2);
        const compareResult2 = service.compareLoginProfile(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLoginProfile(entity1, entity2);
        const compareResult2 = service.compareLoginProfile(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLoginProfile(entity1, entity2);
        const compareResult2 = service.compareLoginProfile(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
