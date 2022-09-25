import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { NotificationComponentsPage, NotificationDeleteDialog, NotificationUpdatePage } from './notification.page-object';

const expect = chai.expect;

describe('Notification e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let notificationComponentsPage: NotificationComponentsPage;
  let notificationUpdatePage: NotificationUpdatePage;
  let notificationDeleteDialog: NotificationDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Notifications', async () => {
    await navBarPage.goToEntity('notification');
    notificationComponentsPage = new NotificationComponentsPage();
    await browser.wait(ec.visibilityOf(notificationComponentsPage.title), 5000);
    expect(await notificationComponentsPage.getTitle()).to.eq('Notifications');
    await browser.wait(
      ec.or(ec.visibilityOf(notificationComponentsPage.entities), ec.visibilityOf(notificationComponentsPage.noResult)),
      1000
    );
  });

  it('should load create Notification page', async () => {
    await notificationComponentsPage.clickOnCreateButton();
    notificationUpdatePage = new NotificationUpdatePage();
    expect(await notificationUpdatePage.getPageTitle()).to.eq('Create or edit a Notification');
    await notificationUpdatePage.cancel();
  });

  it('should create and save Notifications', async () => {
    const nbButtonsBeforeCreate = await notificationComponentsPage.countDeleteButtons();

    await notificationComponentsPage.clickOnCreateButton();

    await promise.all([
      notificationUpdatePage.setUserIdInput('userId'),
      notificationUpdatePage.setTitleInput('title'),
      notificationUpdatePage.setMessageInput('message'),
      notificationUpdatePage.setStatusInput('status'),
      notificationUpdatePage.setTypeInput('type'),
      notificationUpdatePage.setDateOfReadInput('dateOfRead'),
      notificationUpdatePage.setImageUrlInput('imageUrl'),
      notificationUpdatePage.setOriginalPriceInput('originalPrice'),
      notificationUpdatePage.setCurrentPriceInput('currentPrice'),
      notificationUpdatePage.setDiscountInput('discount'),
      notificationUpdatePage.setDiscountTypeInput('discountType'),
    ]);

    await notificationUpdatePage.save();
    expect(await notificationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await notificationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Notification', async () => {
    const nbButtonsBeforeDelete = await notificationComponentsPage.countDeleteButtons();
    await notificationComponentsPage.clickOnLastDeleteButton();

    notificationDeleteDialog = new NotificationDeleteDialog();
    expect(await notificationDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Notification?');
    await notificationDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(notificationComponentsPage.title), 5000);

    expect(await notificationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
