import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SlideComponentsPage, SlideDeleteDialog, SlideUpdatePage } from './slide.page-object';

const expect = chai.expect;

describe('Slide e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let slideComponentsPage: SlideComponentsPage;
  let slideUpdatePage: SlideUpdatePage;
  let slideDeleteDialog: SlideDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Slides', async () => {
    await navBarPage.goToEntity('slide');
    slideComponentsPage = new SlideComponentsPage();
    await browser.wait(ec.visibilityOf(slideComponentsPage.title), 5000);
    expect(await slideComponentsPage.getTitle()).to.eq('Slides');
    await browser.wait(ec.or(ec.visibilityOf(slideComponentsPage.entities), ec.visibilityOf(slideComponentsPage.noResult)), 1000);
  });

  it('should load create Slide page', async () => {
    await slideComponentsPage.clickOnCreateButton();
    slideUpdatePage = new SlideUpdatePage();
    expect(await slideUpdatePage.getPageTitle()).to.eq('Create or edit a Slide');
    await slideUpdatePage.cancel();
  });

  it('should create and save Slides', async () => {
    const nbButtonsBeforeCreate = await slideComponentsPage.countDeleteButtons();

    await slideComponentsPage.clickOnCreateButton();

    await promise.all([
      slideUpdatePage.setTitleInput('title'),
      slideUpdatePage.setSubTitleInput('subTitle'),
      slideUpdatePage.setStatusInput('status'),
      slideUpdatePage.setCountryInput('country'),
      slideUpdatePage.setStartDateInput('startDate'),
      slideUpdatePage.setEndDateInput('endDate'),
      slideUpdatePage.setImageUrlInput('imageUrl'),
      slideUpdatePage.setDealUrlInput('dealUrl'),
      slideUpdatePage.setTagsInput('tags'),
    ]);

    await slideUpdatePage.save();
    expect(await slideUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await slideComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Slide', async () => {
    const nbButtonsBeforeDelete = await slideComponentsPage.countDeleteButtons();
    await slideComponentsPage.clickOnLastDeleteButton();

    slideDeleteDialog = new SlideDeleteDialog();
    expect(await slideDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Slide?');
    await slideDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(slideComponentsPage.title), 5000);

    expect(await slideComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
