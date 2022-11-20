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
  titleInput = element(by.id('field_title'));
  subTitleInput = element(by.id('field_subTitle'));
  iconInput = element(by.id('field_icon'));
  bgColorInput = element(by.id('field_bgColor'));
  countryInput = element(by.id('field_country'));
  codeInput = element(by.id('field_code'));
  statusInput = element(by.id('field_status'));
  displayInput = element(by.id('field_display'));

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

  async setIconInput(icon: string): Promise<void> {
    await this.iconInput.sendKeys(icon);
  }

  async getIconInput(): Promise<string> {
    return await this.iconInput.getAttribute('value');
  }

  async setBgColorInput(bgColor: string): Promise<void> {
    await this.bgColorInput.sendKeys(bgColor);
  }

  async getBgColorInput(): Promise<string> {
    return await this.bgColorInput.getAttribute('value');
  }

  async setCountryInput(country: string): Promise<void> {
    await this.countryInput.sendKeys(country);
  }

  async getCountryInput(): Promise<string> {
    return await this.countryInput.getAttribute('value');
  }

  async setCodeInput(code: string): Promise<void> {
    await this.codeInput.sendKeys(code);
  }

  async getCodeInput(): Promise<string> {
    return await this.codeInput.getAttribute('value');
  }

  async setStatusInput(status: string): Promise<void> {
    await this.statusInput.sendKeys(status);
  }

  async getStatusInput(): Promise<string> {
    return await this.statusInput.getAttribute('value');
  }

  getDisplayInput(): ElementFinder {
    return this.displayInput;
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
