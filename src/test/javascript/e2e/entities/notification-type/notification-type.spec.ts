import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { NotificationTypeComponentsPage, NotificationTypeDeleteDialog, NotificationTypeUpdatePage } from './notification-type.page-object';

const expect = chai.expect;

describe('NotificationType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let notificationTypeComponentsPage: NotificationTypeComponentsPage;
  let notificationTypeUpdatePage: NotificationTypeUpdatePage;
  let notificationTypeDeleteDialog: NotificationTypeDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load NotificationTypes', async () => {
    await navBarPage.goToEntity('notification-type');
    notificationTypeComponentsPage = new NotificationTypeComponentsPage();
    await browser.wait(ec.visibilityOf(notificationTypeComponentsPage.title), 5000);
    expect(await notificationTypeComponentsPage.getTitle()).to.eq('Notification Types');
    await browser.wait(
      ec.or(ec.visibilityOf(notificationTypeComponentsPage.entities), ec.visibilityOf(notificationTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create NotificationType page', async () => {
    await notificationTypeComponentsPage.clickOnCreateButton();
    notificationTypeUpdatePage = new NotificationTypeUpdatePage();
    expect(await notificationTypeUpdatePage.getPageTitle()).to.eq('Create or edit a Notification Type');
    await notificationTypeUpdatePage.cancel();
  });

  it('should create and save NotificationTypes', async () => {
    const nbButtonsBeforeCreate = await notificationTypeComponentsPage.countDeleteButtons();

    await notificationTypeComponentsPage.clickOnCreateButton();

    await promise.all([notificationTypeUpdatePage.setTypeInput('type'), notificationTypeUpdatePage.setDescriptionInput('description')]);

    await notificationTypeUpdatePage.save();
    expect(await notificationTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await notificationTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last NotificationType', async () => {
    const nbButtonsBeforeDelete = await notificationTypeComponentsPage.countDeleteButtons();
    await notificationTypeComponentsPage.clickOnLastDeleteButton();

    notificationTypeDeleteDialog = new NotificationTypeDeleteDialog();
    expect(await notificationTypeDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Notification Type?');
    await notificationTypeDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(notificationTypeComponentsPage.title), 5000);

    expect(await notificationTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
