import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBioProfile } from '../bio-profile.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../bio-profile.test-samples';

import { BioProfileService } from './bio-profile.service';

const requireRestSample: IBioProfile = {
  ...sampleWithRequiredData,
};

describe('BioProfile Service', () => {
  let service: BioProfileService;
  let httpMock: HttpTestingController;
  let expectedResult: IBioProfile | IBioProfile[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BioProfileService);
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

    it('should create a BioProfile', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const bioProfile = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(bioProfile).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BioProfile', () => {
      const bioProfile = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(bioProfile).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BioProfile', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BioProfile', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a BioProfile', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addBioProfileToCollectionIfMissing', () => {
      it('should add a BioProfile to an empty array', () => {
        const bioProfile: IBioProfile = sampleWithRequiredData;
        expectedResult = service.addBioProfileToCollectionIfMissing([], bioProfile);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bioProfile);
      });

      it('should not add a BioProfile to an array that contains it', () => {
        const bioProfile: IBioProfile = sampleWithRequiredData;
        const bioProfileCollection: IBioProfile[] = [
          {
            ...bioProfile,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addBioProfileToCollectionIfMissing(bioProfileCollection, bioProfile);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BioProfile to an array that doesn't contain it", () => {
        const bioProfile: IBioProfile = sampleWithRequiredData;
        const bioProfileCollection: IBioProfile[] = [sampleWithPartialData];
        expectedResult = service.addBioProfileToCollectionIfMissing(bioProfileCollection, bioProfile);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bioProfile);
      });

      it('should add only unique BioProfile to an array', () => {
        const bioProfileArray: IBioProfile[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const bioProfileCollection: IBioProfile[] = [sampleWithRequiredData];
        expectedResult = service.addBioProfileToCollectionIfMissing(bioProfileCollection, ...bioProfileArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bioProfile: IBioProfile = sampleWithRequiredData;
        const bioProfile2: IBioProfile = sampleWithPartialData;
        expectedResult = service.addBioProfileToCollectionIfMissing([], bioProfile, bioProfile2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bioProfile);
        expect(expectedResult).toContain(bioProfile2);
      });

      it('should accept null and undefined values', () => {
        const bioProfile: IBioProfile = sampleWithRequiredData;
        expectedResult = service.addBioProfileToCollectionIfMissing([], null, bioProfile, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bioProfile);
      });

      it('should return initial array if no BioProfile is added', () => {
        const bioProfileCollection: IBioProfile[] = [sampleWithRequiredData];
        expectedResult = service.addBioProfileToCollectionIfMissing(bioProfileCollection, undefined, null);
        expect(expectedResult).toEqual(bioProfileCollection);
      });
    });

    describe('compareBioProfile', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareBioProfile(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareBioProfile(entity1, entity2);
        const compareResult2 = service.compareBioProfile(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareBioProfile(entity1, entity2);
        const compareResult2 = service.compareBioProfile(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareBioProfile(entity1, entity2);
        const compareResult2 = service.compareBioProfile(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
