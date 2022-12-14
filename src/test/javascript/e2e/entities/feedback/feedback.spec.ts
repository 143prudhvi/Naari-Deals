import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FeedbackComponentsPage, FeedbackDeleteDialog, FeedbackUpdatePage } from './feedback.page-object';

const expect = chai.expect;

describe('Feedback e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let feedbackComponentsPage: FeedbackComponentsPage;
  let feedbackUpdatePage: FeedbackUpdatePage;
  let feedbackDeleteDialog: FeedbackDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Feedbacks', async () => {
    await navBarPage.goToEntity('feedback');
    feedbackComponentsPage = new FeedbackComponentsPage();
    await browser.wait(ec.visibilityOf(feedbackComponentsPage.title), 5000);
    expect(await feedbackComponentsPage.getTitle()).to.eq('Feedbacks');
    await browser.wait(ec.or(ec.visibilityOf(feedbackComponentsPage.entities), ec.visibilityOf(feedbackComponentsPage.noResult)), 1000);
  });

  it('should load create Feedback page', async () => {
    await feedbackComponentsPage.clickOnCreateButton();
    feedbackUpdatePage = new FeedbackUpdatePage();
    expect(await feedbackUpdatePage.getPageTitle()).to.eq('Create or edit a Feedback');
    await feedbackUpdatePage.cancel();
  });

  it('should create and save Feedbacks', async () => {
    const nbButtonsBeforeCreate = await feedbackComponentsPage.countDeleteButtons();

    await feedbackComponentsPage.clickOnCreateButton();

    await promise.all([
      feedbackUpdatePage.setTypeInput('type'),
      feedbackUpdatePage.setNameInput('name'),
      feedbackUpdatePage.setEmailInput('email'),
      feedbackUpdatePage.setPhoneInput('phone'),
      feedbackUpdatePage.setUserIdInput('userId'),
      feedbackUpdatePage.setMessageInput('message'),
    ]);

    await feedbackUpdatePage.save();
    expect(await feedbackUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await feedbackComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Feedback', async () => {
    const nbButtonsBeforeDelete = await feedbackComponentsPage.countDeleteButtons();
    await feedbackComponentsPage.clickOnLastDeleteButton();

    feedbackDeleteDialog = new FeedbackDeleteDialog();
    expect(await feedbackDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Feedback?');
    await feedbackDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(feedbackComponentsPage.title), 5000);

    expect(await feedbackComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
