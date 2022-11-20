import { element, by, ElementFinder } from 'protractor';

export class SlideComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-slide div table .btn-danger'));
  title = element.all(by.css('jhi-slide div h2#page-heading span')).first();
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

export class SlideUpdatePage {
  pageTitle = element(by.id('jhi-slide-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  titleInput = element(by.id('field_title'));
  subTitleInput = element(by.id('field_subTitle'));
  statusInput = element(by.id('field_status'));
  countryInput = element(by.id('field_country'));
  startDateInput = element(by.id('field_startDate'));
  endDateInput = element(by.id('field_endDate'));
  imageUrlInput = element(by.id('field_imageUrl'));
  merchantIconInput = element(by.id('field_merchantIcon'));
  dealUrlInput = element(by.id('field_dealUrl'));
  tagsInput = element(by.id('field_tags'));

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

  async setSubTitleInput(subTitle: string): Promise<void> {
    await this.subTitleInput.sendKeys(subTitle);
  }

  async getSubTitleInput(): Promise<string> {
    return await this.subTitleInput.getAttribute('value');
  }

  async setStatusInput(status: string): Promise<void> {
    await this.statusInput.sendKeys(status);
  }

  async getStatusInput(): Promise<string> {
    return await this.statusInput.getAttribute('value');
  }

  async setCountryInput(country: string): Promise<void> {
    await this.countryInput.sendKeys(country);
  }

  async getCountryInput(): Promise<string> {
    return await this.countryInput.getAttribute('value');
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

  async setImageUrlInput(imageUrl: string): Promise<void> {
    await this.imageUrlInput.sendKeys(imageUrl);
  }

  async getImageUrlInput(): Promise<string> {
    return await this.imageUrlInput.getAttribute('value');
  }

  async setMerchantIconInput(merchantIcon: string): Promise<void> {
    await this.merchantIconInput.sendKeys(merchantIcon);
  }

  async getMerchantIconInput(): Promise<string> {
    return await this.merchantIconInput.getAttribute('value');
  }

  async setDealUrlInput(dealUrl: string): Promise<void> {
    await this.dealUrlInput.sendKeys(dealUrl);
  }

  async getDealUrlInput(): Promise<string> {
    return await this.dealUrlInput.getAttribute('value');
  }

  async setTagsInput(tags: string): Promise<void> {
    await this.tagsInput.sendKeys(tags);
  }

  async getTagsInput(): Promise<string> {
    return await this.tagsInput.getAttribute('value');
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

export class SlideDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-slide-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-slide'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
