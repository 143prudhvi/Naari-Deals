import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BrandComponentsPage, BrandDeleteDialog, BrandUpdatePage } from './brand.page-object';

const expect = chai.expect;

describe('Brand e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let brandComponentsPage: BrandComponentsPage;
  let brandUpdatePage: BrandUpdatePage;
  let brandDeleteDialog: BrandDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Brands', async () => {
    await navBarPage.goToEntity('brand');
    brandComponentsPage = new BrandComponentsPage();
    await browser.wait(ec.visibilityOf(brandComponentsPage.title), 5000);
    expect(await brandComponentsPage.getTitle()).to.eq('Brands');
    await browser.wait(ec.or(ec.visibilityOf(brandComponentsPage.entities), ec.visibilityOf(brandComponentsPage.noResult)), 1000);
  });

  it('should load create Brand page', async () => {
    await brandComponentsPage.clickOnCreateButton();
    brandUpdatePage = new BrandUpdatePage();
    expect(await brandUpdatePage.getPageTitle()).to.eq('Create or edit a Brand');
    await brandUpdatePage.cancel();
  });

  it('should create and save Brands', async () => {
    const nbButtonsBeforeCreate = await brandComponentsPage.countDeleteButtons();

    await brandComponentsPage.clickOnCreateButton();

    await promise.all([
      brandUpdatePage.setTitleInput('title'),
      brandUpdatePage.setSubTitleInput('subTitle'),
      brandUpdatePage.setStatusInput('status'),
      brandUpdatePage.setCountryInput('country'),
      brandUpdatePage.setImageUrlInput('imageUrl'),
    ]);

    await brandUpdatePage.save();
    expect(await brandUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await brandComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Brand', async () => {
    const nbButtonsBeforeDelete = await brandComponentsPage.countDeleteButtons();
    await brandComponentsPage.clickOnLastDeleteButton();

    brandDeleteDialog = new BrandDeleteDialog();
    expect(await brandDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Brand?');
    await brandDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(brandComponentsPage.title), 5000);

    expect(await brandComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
