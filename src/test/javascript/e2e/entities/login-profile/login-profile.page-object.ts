import { element, by, ElementFinder } from 'protractor';

export class LoginProfileComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-login-profile div table .btn-danger'));
  title = element.all(by.css('jhi-login-profile div h2#page-heading span')).first();
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

export class LoginProfileUpdatePage {
  pageTitle = element(by.id('jhi-login-profile-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  userNameInput = element(by.id('field_userName'));
  userIdInput = element(by.id('field_userId'));
  memberTypeInput = element(by.id('field_memberType'));
  phoneNumberInput = element(by.id('field_phoneNumber'));
  emailIdInput = element(by.id('field_emailId'));
  passwordInput = element(by.id('field_password'));
  activationStatusInput = element(by.id('field_activationStatus'));
  activationCodeInput = element(by.id('field_activationCode'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setUserNameInput(userName: string): Promise<void> {
    await this.userNameInput.sendKeys(userName);
  }

  async getUserNameInput(): Promise<string> {
    return await this.userNameInput.getAttribute('value');
  }

  async setUserIdInput(userId: string): Promise<void> {
    await this.userIdInput.sendKeys(userId);
  }

  async getUserIdInput(): Promise<string> {
    return await this.userIdInput.getAttribute('value');
  }

  async setMemberTypeInput(memberType: string): Promise<void> {
    await this.memberTypeInput.sendKeys(memberType);
  }

  async getMemberTypeInput(): Promise<string> {
    return await this.memberTypeInput.getAttribute('value');
  }

  async setPhoneNumberInput(phoneNumber: string): Promise<void> {
    await this.phoneNumberInput.sendKeys(phoneNumber);
  }

  async getPhoneNumberInput(): Promise<string> {
    return await this.phoneNumberInput.getAttribute('value');
  }

  async setEmailIdInput(emailId: string): Promise<void> {
    await this.emailIdInput.sendKeys(emailId);
  }

  async getEmailIdInput(): Promise<string> {
    return await this.emailIdInput.getAttribute('value');
  }

  async setPasswordInput(password: string): Promise<void> {
    await this.passwordInput.sendKeys(password);
  }

  async getPasswordInput(): Promise<string> {
    return await this.passwordInput.getAttribute('value');
  }

  async setActivationStatusInput(activationStatus: string): Promise<void> {
    await this.activationStatusInput.sendKeys(activationStatus);
  }

  async getActivationStatusInput(): Promise<string> {
    return await this.activationStatusInput.getAttribute('value');
  }

  async setActivationCodeInput(activationCode: string): Promise<void> {
    await this.activationCodeInput.sendKeys(activationCode);
  }

  async getActivationCodeInput(): Promise<string> {
    return await this.activationCodeInput.getAttribute('value');
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

export class LoginProfileDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-loginProfile-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-loginProfile'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
