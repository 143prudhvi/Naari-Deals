import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  EmailSubscriptionComponentsPage,
  EmailSubscriptionDeleteDialog,
  EmailSubscriptionUpdatePage,
} from './email-subscription.page-object';

const expect = chai.expect;

describe('EmailSubscription e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let emailSubscriptionComponentsPage: EmailSubscriptionComponentsPage;
  let emailSubscriptionUpdatePage: EmailSubscriptionUpdatePage;
  let emailSubscriptionDeleteDialog: EmailSubscriptionDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EmailSubscriptions', async () => {
    await navBarPage.goToEntity('email-subscription');
    emailSubscriptionComponentsPage = new EmailSubscriptionComponentsPage();
    await browser.wait(ec.visibilityOf(emailSubscriptionComponentsPage.title), 5000);
    expect(await emailSubscriptionComponentsPage.getTitle()).to.eq('Email Subscriptions');
    await browser.wait(
      ec.or(ec.visibilityOf(emailSubscriptionComponentsPage.entities), ec.visibilityOf(emailSubscriptionComponentsPage.noResult)),
      1000
    );
  });

  it('should load create EmailSubscription page', async () => {
    await emailSubscriptionComponentsPage.clickOnCreateButton();
    emailSubscriptionUpdatePage = new EmailSubscriptionUpdatePage();
    expect(await emailSubscriptionUpdatePage.getPageTitle()).to.eq('Create or edit a Email Subscription');
    await emailSubscriptionUpdatePage.cancel();
  });

  it('should create and save EmailSubscriptions', async () => {
    const nbButtonsBeforeCreate = await emailSubscriptionComponentsPage.countDeleteButtons();

    await emailSubscriptionComponentsPage.clickOnCreateButton();

    await promise.all([emailSubscriptionUpdatePage.setEmailInput('email'), emailSubscriptionUpdatePage.setCountryInput('country')]);

    await emailSubscriptionUpdatePage.save();
    expect(await emailSubscriptionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await emailSubscriptionComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last EmailSubscription', async () => {
    const nbButtonsBeforeDelete = await emailSubscriptionComponentsPage.countDeleteButtons();
    await emailSubscriptionComponentsPage.clickOnLastDeleteButton();

    emailSubscriptionDeleteDialog = new EmailSubscriptionDeleteDialog();
    expect(await emailSubscriptionDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Email Subscription?');
    await emailSubscriptionDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(emailSubscriptionComponentsPage.title), 5000);

    expect(await emailSubscriptionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
