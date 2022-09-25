import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DealTypeComponentsPage, DealTypeDeleteDialog, DealTypeUpdatePage } from './deal-type.page-object';

const expect = chai.expect;

describe('DealType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let dealTypeComponentsPage: DealTypeComponentsPage;
  let dealTypeUpdatePage: DealTypeUpdatePage;
  let dealTypeDeleteDialog: DealTypeDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DealTypes', async () => {
    await navBarPage.goToEntity('deal-type');
    dealTypeComponentsPage = new DealTypeComponentsPage();
    await browser.wait(ec.visibilityOf(dealTypeComponentsPage.title), 5000);
    expect(await dealTypeComponentsPage.getTitle()).to.eq('Deal Types');
    await browser.wait(ec.or(ec.visibilityOf(dealTypeComponentsPage.entities), ec.visibilityOf(dealTypeComponentsPage.noResult)), 1000);
  });

  it('should load create DealType page', async () => {
    await dealTypeComponentsPage.clickOnCreateButton();
    dealTypeUpdatePage = new DealTypeUpdatePage();
    expect(await dealTypeUpdatePage.getPageTitle()).to.eq('Create or edit a Deal Type');
    await dealTypeUpdatePage.cancel();
  });

  it('should create and save DealTypes', async () => {
    const nbButtonsBeforeCreate = await dealTypeComponentsPage.countDeleteButtons();

    await dealTypeComponentsPage.clickOnCreateButton();

    await promise.all([
      dealTypeUpdatePage.setDealTypeInput('dealType'),
      dealTypeUpdatePage.setDescriptionInput('description'),
      dealTypeUpdatePage.setImageUrlInput('imageUrl'),
    ]);

    await dealTypeUpdatePage.save();
    expect(await dealTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await dealTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last DealType', async () => {
    const nbButtonsBeforeDelete = await dealTypeComponentsPage.countDeleteButtons();
    await dealTypeComponentsPage.clickOnLastDeleteButton();

    dealTypeDeleteDialog = new DealTypeDeleteDialog();
    expect(await dealTypeDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Deal Type?');
    await dealTypeDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(dealTypeComponentsPage.title), 5000);

    expect(await dealTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
