import { element, by, ElementFinder } from 'protractor';

export class DealTypeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-deal-type div table .btn-danger'));
  title = element.all(by.css('jhi-deal-type div h2#page-heading span')).first();
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

export class DealTypeUpdatePage {
  pageTitle = element(by.id('jhi-deal-type-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  dealTypeInput = element(by.id('field_dealType'));
  descriptionInput = element(by.id('field_description'));
  imageUrlInput = element(by.id('field_imageUrl'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setDealTypeInput(dealType: string): Promise<void> {
    await this.dealTypeInput.sendKeys(dealType);
  }

  async getDealTypeInput(): Promise<string> {
    return await this.dealTypeInput.getAttribute('value');
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

export class DealTypeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-dealType-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-dealType'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
