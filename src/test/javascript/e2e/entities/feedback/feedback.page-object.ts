import { element, by, ElementFinder } from 'protractor';

export class FeedbackComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-feedback div table .btn-danger'));
  title = element.all(by.css('jhi-feedback div h2#page-heading span')).first();
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

export class FeedbackUpdatePage {
  pageTitle = element(by.id('jhi-feedback-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  typeInput = element(by.id('field_type'));
  nameInput = element(by.id('field_name'));
  emailInput = element(by.id('field_email'));
  phoneInput = element(by.id('field_phone'));
  userIdInput = element(by.id('field_userId'));
  messageInput = element(by.id('field_message'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setTypeInput(type: string): Promise<void> {
    await this.typeInput.sendKeys(type);
  }

  async getTypeInput(): Promise<string> {
    return await this.typeInput.getAttribute('value');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setEmailInput(email: string): Promise<void> {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput(): Promise<string> {
    return await this.emailInput.getAttribute('value');
  }

  async setPhoneInput(phone: string): Promise<void> {
    await this.phoneInput.sendKeys(phone);
  }

  async getPhoneInput(): Promise<string> {
    return await this.phoneInput.getAttribute('value');
  }

  async setUserIdInput(userId: string): Promise<void> {
    await this.userIdInput.sendKeys(userId);
  }

  async getUserIdInput(): Promise<string> {
    return await this.userIdInput.getAttribute('value');
  }

  async setMessageInput(message: string): Promise<void> {
    await this.messageInput.sendKeys(message);
  }

  async getMessageInput(): Promise<string> {
    return await this.messageInput.getAttribute('value');
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

export class FeedbackDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-feedback-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-feedback'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
