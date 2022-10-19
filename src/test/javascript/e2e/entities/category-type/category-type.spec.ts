import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CategoryTypeComponentsPage, CategoryTypeDeleteDialog, CategoryTypeUpdatePage } from './category-type.page-object';

const expect = chai.expect;

describe('CategoryType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let categoryTypeComponentsPage: CategoryTypeComponentsPage;
  let categoryTypeUpdatePage: CategoryTypeUpdatePage;
  let categoryTypeDeleteDialog: CategoryTypeDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CategoryTypes', async () => {
    await navBarPage.goToEntity('category-type');
    categoryTypeComponentsPage = new CategoryTypeComponentsPage();
    await browser.wait(ec.visibilityOf(categoryTypeComponentsPage.title), 5000);
    expect(await categoryTypeComponentsPage.getTitle()).to.eq('Category Types');
    await browser.wait(
      ec.or(ec.visibilityOf(categoryTypeComponentsPage.entities), ec.visibilityOf(categoryTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CategoryType page', async () => {
    await categoryTypeComponentsPage.clickOnCreateButton();
    categoryTypeUpdatePage = new CategoryTypeUpdatePage();
    expect(await categoryTypeUpdatePage.getPageTitle()).to.eq('Create or edit a Category Type');
    await categoryTypeUpdatePage.cancel();
  });

  it('should create and save CategoryTypes', async () => {
    const nbButtonsBeforeCreate = await categoryTypeComponentsPage.countDeleteButtons();

    await categoryTypeComponentsPage.clickOnCreateButton();

    await promise.all([
      categoryTypeUpdatePage.setTitleInput('title'),
      categoryTypeUpdatePage.setSubTitleInput('subTitle'),
      categoryTypeUpdatePage.setIconInput('icon'),
      categoryTypeUpdatePage.setBgColorInput('bgColor'),
      categoryTypeUpdatePage.setCountryInput('country'),
      categoryTypeUpdatePage.setCodeInput('code'),
      categoryTypeUpdatePage.setStatusInput('status'),
    ]);

    await categoryTypeUpdatePage.save();
    expect(await categoryTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await categoryTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last CategoryType', async () => {
    const nbButtonsBeforeDelete = await categoryTypeComponentsPage.countDeleteButtons();
    await categoryTypeComponentsPage.clickOnLastDeleteButton();

    categoryTypeDeleteDialog = new CategoryTypeDeleteDialog();
    expect(await categoryTypeDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Category Type?');
    await categoryTypeDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(categoryTypeComponentsPage.title), 5000);

    expect(await categoryTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
