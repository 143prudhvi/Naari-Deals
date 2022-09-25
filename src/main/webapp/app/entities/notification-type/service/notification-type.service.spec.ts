import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INotificationType } from '../notification-type.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../notification-type.test-samples';

import { NotificationTypeService } from './notification-type.service';

const requireRestSample: INotificationType = {
  ...sampleWithRequiredData,
};

describe('NotificationType Service', () => {
  let service: NotificationTypeService;
  let httpMock: HttpTestingController;
  let expectedResult: INotificationType | INotificationType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(NotificationTypeService);
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

    it('should create a NotificationType', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const notificationType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(notificationType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a NotificationType', () => {
      const notificationType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(notificationType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a NotificationType', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of NotificationType', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a NotificationType', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addNotificationTypeToCollectionIfMissing', () => {
      it('should add a NotificationType to an empty array', () => {
        const notificationType: INotificationType = sampleWithRequiredData;
        expectedResult = service.addNotificationTypeToCollectionIfMissing([], notificationType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(notificationType);
      });

      it('should not add a NotificationType to an array that contains it', () => {
        const notificationType: INotificationType = sampleWithRequiredData;
        const notificationTypeCollection: INotificationType[] = [
          {
            ...notificationType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addNotificationTypeToCollectionIfMissing(notificationTypeCollection, notificationType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a NotificationType to an array that doesn't contain it", () => {
        const notificationType: INotificationType = sampleWithRequiredData;
        const notificationTypeCollection: INotificationType[] = [sampleWithPartialData];
        expectedResult = service.addNotificationTypeToCollectionIfMissing(notificationTypeCollection, notificationType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(notificationType);
      });

      it('should add only unique NotificationType to an array', () => {
        const notificationTypeArray: INotificationType[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const notificationTypeCollection: INotificationType[] = [sampleWithRequiredData];
        expectedResult = service.addNotificationTypeToCollectionIfMissing(notificationTypeCollection, ...notificationTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const notificationType: INotificationType = sampleWithRequiredData;
        const notificationType2: INotificationType = sampleWithPartialData;
        expectedResult = service.addNotificationTypeToCollectionIfMissing([], notificationType, notificationType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(notificationType);
        expect(expectedResult).toContain(notificationType2);
      });

      it('should accept null and undefined values', () => {
        const notificationType: INotificationType = sampleWithRequiredData;
        expectedResult = service.addNotificationTypeToCollectionIfMissing([], null, notificationType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(notificationType);
      });

      it('should return initial array if no NotificationType is added', () => {
        const notificationTypeCollection: INotificationType[] = [sampleWithRequiredData];
        expectedResult = service.addNotificationTypeToCollectionIfMissing(notificationTypeCollection, undefined, null);
        expect(expectedResult).toEqual(notificationTypeCollection);
      });
    });

    describe('compareNotificationType', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareNotificationType(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareNotificationType(entity1, entity2);
        const compareResult2 = service.compareNotificationType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareNotificationType(entity1, entity2);
        const compareResult2 = service.compareNotificationType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareNotificationType(entity1, entity2);
        const compareResult2 = service.compareNotificationType(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
