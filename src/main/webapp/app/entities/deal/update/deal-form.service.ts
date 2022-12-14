import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDeal, NewDeal } from '../deal.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDeal for edit and NewDealFormGroupInput for create.
 */
type DealFormGroupInput = IDeal | PartialWithRequiredKeyOf<NewDeal>;

type DealFormDefaults = Pick<NewDeal, 'id' | 'approved' | 'expired'>;

type DealFormGroupContent = {
  id: FormControl<IDeal['id'] | NewDeal['id']>;
  title: FormControl<IDeal['title']>;
  description: FormControl<IDeal['description']>;
  imageUrl: FormControl<IDeal['imageUrl']>;
  dealUrl: FormControl<IDeal['dealUrl']>;
  highlight: FormControl<IDeal['highlight']>;
  postedBy: FormControl<IDeal['postedBy']>;
  postedDate: FormControl<IDeal['postedDate']>;
  startDate: FormControl<IDeal['startDate']>;
  endDate: FormControl<IDeal['endDate']>;
  originalPrice: FormControl<IDeal['originalPrice']>;
  currentPrice: FormControl<IDeal['currentPrice']>;
  priceTag: FormControl<IDeal['priceTag']>;
  discount: FormControl<IDeal['discount']>;
  discountType: FormControl<IDeal['discountType']>;
  active: FormControl<IDeal['active']>;
  approved: FormControl<IDeal['approved']>;
  country: FormControl<IDeal['country']>;
  city: FormControl<IDeal['city']>;
  pinCode: FormControl<IDeal['pinCode']>;
  merchant: FormControl<IDeal['merchant']>;
  category: FormControl<IDeal['category']>;
  tags: FormControl<IDeal['tags']>;
  brand: FormControl<IDeal['brand']>;
  expired: FormControl<IDeal['expired']>;
};

export type DealFormGroup = FormGroup<DealFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DealFormService {
  createDealFormGroup(deal: DealFormGroupInput = { id: null }): DealFormGroup {
    const dealRawValue = {
      ...this.getFormDefaults(),
      ...deal,
    };
    return new FormGroup<DealFormGroupContent>({
      id: new FormControl(
        { value: dealRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      title: new FormControl(dealRawValue.title),
      description: new FormControl(dealRawValue.description),
      imageUrl: new FormControl(dealRawValue.imageUrl),
      dealUrl: new FormControl(dealRawValue.dealUrl),
      highlight: new FormControl(dealRawValue.highlight),
      postedBy: new FormControl(dealRawValue.postedBy),
      postedDate: new FormControl(dealRawValue.postedDate),
      startDate: new FormControl(dealRawValue.startDate),
      endDate: new FormControl(dealRawValue.endDate),
      originalPrice: new FormControl(dealRawValue.originalPrice),
      currentPrice: new FormControl(dealRawValue.currentPrice),
      priceTag: new FormControl(dealRawValue.priceTag),
      discount: new FormControl(dealRawValue.discount),
      discountType: new FormControl(dealRawValue.discountType),
      active: new FormControl(dealRawValue.active),
      approved: new FormControl(dealRawValue.approved),
      country: new FormControl(dealRawValue.country),
      city: new FormControl(dealRawValue.city),
      pinCode: new FormControl(dealRawValue.pinCode),
      merchant: new FormControl(dealRawValue.merchant),
      category: new FormControl(dealRawValue.category),
      tags: new FormControl(dealRawValue.tags),
      brand: new FormControl(dealRawValue.brand),
      expired: new FormControl(dealRawValue.expired),
    });
  }

  getDeal(form: DealFormGroup): IDeal | NewDeal {
    return form.getRawValue() as IDeal | NewDeal;
  }

  resetForm(form: DealFormGroup, deal: DealFormGroupInput): void {
    const dealRawValue = { ...this.getFormDefaults(), ...deal };
    form.reset(
      {
        ...dealRawValue,
        id: { value: dealRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DealFormDefaults {
    return {
      id: null,
      approved: false,
      expired: false,
    };
  }
}
