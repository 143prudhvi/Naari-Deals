import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEmailSubscription } from '../email-subscription.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../email-subscription.test-samples';

import { EmailSubscriptionService } from './email-subscription.service';

const requireRestSample: IEmailSubscription = {
  ...sampleWithRequiredData,
};

describe('EmailSubscription Service', () => {
  let service: EmailSubscriptionService;
  let httpMock: HttpTestingController;
  let expectedResult: IEmailSubscription | IEmailSubscription[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EmailSubscriptionService);
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

    it('should create a EmailSubscription', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const emailSubscription = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(emailSubscription).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EmailSubscription', () => {
      const emailSubscription = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(emailSubscription).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EmailSubscription', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EmailSubscription', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EmailSubscription', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEmailSubscriptionToCollectionIfMissing', () => {
      it('should add a EmailSubscription to an empty array', () => {
        const emailSubscription: IEmailSubscription = sampleWithRequiredData;
        expectedResult = service.addEmailSubscriptionToCollectionIfMissing([], emailSubscription);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(emailSubscription);
      });

      it('should not add a EmailSubscription to an array that contains it', () => {
        const emailSubscription: IEmailSubscription = sampleWithRequiredData;
        const emailSubscriptionCollection: IEmailSubscription[] = [
          {
            ...emailSubscription,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEmailSubscriptionToCollectionIfMissing(emailSubscriptionCollection, emailSubscription);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EmailSubscription to an array that doesn't contain it", () => {
        const emailSubscription: IEmailSubscription = sampleWithRequiredData;
        const emailSubscriptionCollection: IEmailSubscription[] = [sampleWithPartialData];
        expectedResult = service.addEmailSubscriptionToCollectionIfMissing(emailSubscriptionCollection, emailSubscription);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(emailSubscription);
      });

      it('should add only unique EmailSubscription to an array', () => {
        const emailSubscriptionArray: IEmailSubscription[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const emailSubscriptionCollection: IEmailSubscription[] = [sampleWithRequiredData];
        expectedResult = service.addEmailSubscriptionToCollectionIfMissing(emailSubscriptionCollection, ...emailSubscriptionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const emailSubscription: IEmailSubscription = sampleWithRequiredData;
        const emailSubscription2: IEmailSubscription = sampleWithPartialData;
        expectedResult = service.addEmailSubscriptionToCollectionIfMissing([], emailSubscription, emailSubscription2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(emailSubscription);
        expect(expectedResult).toContain(emailSubscription2);
      });

      it('should accept null and undefined values', () => {
        const emailSubscription: IEmailSubscription = sampleWithRequiredData;
        expectedResult = service.addEmailSubscriptionToCollectionIfMissing([], null, emailSubscription, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(emailSubscription);
      });

      it('should return initial array if no EmailSubscription is added', () => {
        const emailSubscriptionCollection: IEmailSubscription[] = [sampleWithRequiredData];
        expectedResult = service.addEmailSubscriptionToCollectionIfMissing(emailSubscriptionCollection, undefined, null);
        expect(expectedResult).toEqual(emailSubscriptionCollection);
      });
    });

    describe('compareEmailSubscription', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEmailSubscription(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEmailSubscription(entity1, entity2);
        const compareResult2 = service.compareEmailSubscription(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEmailSubscription(entity1, entity2);
        const compareResult2 = service.compareEmailSubscription(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEmailSubscription(entity1, entity2);
        const compareResult2 = service.compareEmailSubscription(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
