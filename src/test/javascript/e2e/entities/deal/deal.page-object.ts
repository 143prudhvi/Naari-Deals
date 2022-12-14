import { element, by, ElementFinder } from 'protractor';

export class DealComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-deal div table .btn-danger'));
  title = element.all(by.css('jhi-deal div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getText();
  }
}

export class DealUpdatePage {
  pageTitle = element(by.id('jhi-deal-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  titleInput = element(by.id('field_title'));
  descriptionInput = element(by.id('field_description'));
  imageUrlInput = element(by.id('field_imageUrl'));
  dealUrlInput = element(by.id('field_dealUrl'));
  highlightInput = element(by.id('field_highlight'));
  postedByInput = element(by.id('field_postedBy'));
  postedDateInput = element(by.id('field_postedDate'));
  startDateInput = element(by.id('field_startDate'));
  endDateInput = element(by.id('field_endDate'));
  originalPriceInput = element(by.id('field_originalPrice'));
  currentPriceInput = element(by.id('field_currentPrice'));
  priceTagInput = element(by.id('field_priceTag'));
  discountInput = element(by.id('field_discount'));
  discountTypeInput = element(by.id('field_discountType'));
  activeInput = element(by.id('field_active'));
  approvedInput = element(by.id('field_approved'));
  countryInput = element(by.id('field_country'));
  cityInput = element(by.id('field_city'));
  pinCodeInput = element(by.id('field_pinCode'));
  merchantInput = element(by.id('field_merchant'));
  categoryInput = element(by.id('field_category'));
  tagsInput = element(by.id('field_tags'));
  brandInput = element(by.id('field_brand'));
  expiredInput = element(by.id('field_expired'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setTitleInput(title: string): Promise<void> {
    await this.titleInput.sendKeys(title);
  }

  async getTitleInput(): Promise<string> {
    return await this.titleInput.getAttribute('value');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setImageUrlInput(imageUrl: string): Promise<void> {
    await this.imageUrlInput.sendKeys(imageUrl);
  }

  async getImageUrlInput(): Promise<string> {
    return await this.imageUrlInput.getAttribute('value');
  }

  async setDealUrlInput(dealUrl: string): Promise<void> {
    await this.dealUrlInput.sendKeys(dealUrl);
  }

  async getDealUrlInput(): Promise<string> {
    return await this.dealUrlInput.getAttribute('value');
  }

  async setHighlightInput(highlight: string): Promise<void> {
    await this.highlightInput.sendKeys(highlight);
  }

  async getHighlightInput(): Promise<string> {
    return await this.highlightInput.getAttribute('value');
  }

  async setPostedByInput(postedBy: string): Promise<void> {
    await this.postedByInput.sendKeys(postedBy);
  }

  async getPostedByInput(): Promise<string> {
    return await this.postedByInput.getAttribute('value');
  }

  async setPostedDateInput(postedDate: string): Promise<void> {
    await this.postedDateInput.sendKeys(postedDate);
  }

  async getPostedDateInput(): Promise<string> {
    return await this.postedDateInput.getAttribute('value');
  }

  async setStartDateInput(startDate: string): Promise<void> {
    await this.startDateInput.sendKeys(startDate);
  }

  async getStartDateInput(): Promise<string> {
    return await this.startDateInput.getAttribute('value');
  }

  async setEndDateInput(endDate: string): Promise<void> {
    await this.endDateInput.sendKeys(endDate);
  }

  async getEndDateInput(): Promise<string> {
    return await this.endDateInput.getAttribute('value');
  }

  async setOriginalPriceInput(originalPrice: string): Promise<void> {
    await this.originalPriceInput.sendKeys(originalPrice);
  }

  async getOriginalPriceInput(): Promise<string> {
    return await this.originalPriceInput.getAttribute('value');
  }

  async setCurrentPriceInput(currentPrice: string): Promise<void> {
    await this.currentPriceInput.sendKeys(currentPrice);
  }

  async getCurrentPriceInput(): Promise<string> {
    return await this.currentPriceInput.getAttribute('value');
  }

  async setPriceTagInput(priceTag: string): Promise<void> {
    await this.priceTagInput.sendKeys(priceTag);
  }

  async getPriceTagInput(): Promise<string> {
    return await this.priceTagInput.getAttribute('value');
  }

  async setDiscountInput(discount: string): Promise<void> {
    await this.discountInput.sendKeys(discount);
  }

  async getDiscountInput(): Promise<string> {
    return await this.discountInput.getAttribute('value');
  }

  async setDiscountTypeInput(discountType: string): Promise<void> {
    await this.discountTypeInput.sendKeys(discountType);
  }

  async getDiscountTypeInput(): Promise<string> {
    return await this.discountTypeInput.getAttribute('value');
  }

  async setActiveInput(active: string): Promise<void> {
    await this.activeInput.sendKeys(active);
  }

  async getActiveInput(): Promise<string> {
    return await this.activeInput.getAttribute('value');
  }

  getApprovedInput(): ElementFinder {
    return this.approvedInput;
  }

  async setCountryInput(country: string): Promise<void> {
    await this.countryInput.sendKeys(country);
  }

  async getCountryInput(): Promise<string> {
    return await this.countryInput.getAttribute('value');
  }

  async setCityInput(city: string): Promise<void> {
    await this.cityInput.sendKeys(city);
  }

  async getCityInput(): Promise<string> {
    return await this.cityInput.getAttribute('value');
  }

  async setPinCodeInput(pinCode: string): Promise<void> {
    await this.pinCodeInput.sendKeys(pinCode);
  }

  async getPinCodeInput(): Promise<string> {
    return await this.pinCodeInput.getAttribute('value');
  }

  async setMerchantInput(merchant: string): Promise<void> {
    await this.merchantInput.sendKeys(merchant);
  }

  async getMerchantInput(): Promise<string> {
    return await this.merchantInput.getAttribute('value');
  }

  async setCategoryInput(category: string): Promise<void> {
    await this.categoryInput.sendKeys(category);
  }

  async getCategoryInput(): Promise<string> {
    return await this.categoryInput.getAttribute('value');
  }

  async setTagsInput(tags: string): Promise<void> {
    await this.tagsInput.sendKeys(tags);
  }

  async getTagsInput(): Promise<string> {
    return await this.tagsInput.getAttribute('value');
  }

  async setBrandInput(brand: string): Promise<void> {
    await this.brandInput.sendKeys(brand);
  }

  async getBrandInput(): Promise<string> {
    return await this.brandInput.getAttribute('value');
  }

  getExpiredInput(): ElementFinder {
    return this.expiredInput;
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class DealDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-deal-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-deal'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
