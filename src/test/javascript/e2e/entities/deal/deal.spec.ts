import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DealComponentsPage, DealDeleteDialog, DealUpdatePage } from './deal.page-object';

const expect = chai.expect;

describe('Deal e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let dealComponentsPage: DealComponentsPage;
  let dealUpdatePage: DealUpdatePage;
  let dealDeleteDialog: DealDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Deals', async () => {
    await navBarPage.goToEntity('deal');
    dealComponentsPage = new DealComponentsPage();
    await browser.wait(ec.visibilityOf(dealComponentsPage.title), 5000);
    expect(await dealComponentsPage.getTitle()).to.eq('Deals');
    await browser.wait(ec.or(ec.visibilityOf(dealComponentsPage.entities), ec.visibilityOf(dealComponentsPage.noResult)), 1000);
  });

  it('should load create Deal page', async () => {
    await dealComponentsPage.clickOnCreateButton();
    dealUpdatePage = new DealUpdatePage();
    expect(await dealUpdatePage.getPageTitle()).to.eq('Create or edit a Deal');
    await dealUpdatePage.cancel();
  });

  it('should create and save Deals', async () => {
    const nbButtonsBeforeCreate = await dealComponentsPage.countDeleteButtons();

    await dealComponentsPage.clickOnCreateButton();

    await promise.all([
      dealUpdatePage.setTitleInput('title'),
      dealUpdatePage.setDescriptionInput('description'),
      dealUpdatePage.setImageUrlInput('imageUrl'),
      dealUpdatePage.setDealUrlInput('dealUrl'),
      dealUpdatePage.setPostedByInput('postedBy'),
      dealUpdatePage.setPostedDateInput('postedDate'),
      dealUpdatePage.setStartDateInput('startDate'),
      dealUpdatePage.setEndDateInput('endDate'),
      dealUpdatePage.setOriginalPriceInput('originalPrice'),
      dealUpdatePage.setCurrentPriceInput('currentPrice'),
      dealUpdatePage.setDiscountInput('discount'),
      dealUpdatePage.setDiscountTypeInput('discountType'),
      dealUpdatePage.setActiveInput('active'),
      dealUpdatePage.getApprovedInput().click(),
      dealUpdatePage.setCountryInput('country'),
      dealUpdatePage.setCityInput('city'),
      dealUpdatePage.setPinCodeInput('pinCode'),
      dealUpdatePage.setMerchantInput('merchant'),
      dealUpdatePage.setCategoryInput('category'),
      dealUpdatePage.setTagsInput('tags'),
      dealUpdatePage.setBrandInput('brand'),
      dealUpdatePage.getExpiredInput().click(),
    ]);

    await dealUpdatePage.save();
    expect(await dealUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await dealComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Deal', async () => {
    const nbButtonsBeforeDelete = await dealComponentsPage.countDeleteButtons();
    await dealComponentsPage.clickOnLastDeleteButton();

    dealDeleteDialog = new DealDeleteDialog();
    expect(await dealDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Deal?');
    await dealDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(dealComponentsPage.title), 5000);

    expect(await dealComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
