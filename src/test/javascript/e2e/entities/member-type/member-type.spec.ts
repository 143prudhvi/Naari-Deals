import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MemberTypeComponentsPage, MemberTypeDeleteDialog, MemberTypeUpdatePage } from './member-type.page-object';

const expect = chai.expect;

describe('MemberType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let memberTypeComponentsPage: MemberTypeComponentsPage;
  let memberTypeUpdatePage: MemberTypeUpdatePage;
  let memberTypeDeleteDialog: MemberTypeDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load MemberTypes', async () => {
    await navBarPage.goToEntity('member-type');
    memberTypeComponentsPage = new MemberTypeComponentsPage();
    await browser.wait(ec.visibilityOf(memberTypeComponentsPage.title), 5000);
    expect(await memberTypeComponentsPage.getTitle()).to.eq('Member Types');
    await browser.wait(ec.or(ec.visibilityOf(memberTypeComponentsPage.entities), ec.visibilityOf(memberTypeComponentsPage.noResult)), 1000);
  });

  it('should load create MemberType page', async () => {
    await memberTypeComponentsPage.clickOnCreateButton();
    memberTypeUpdatePage = new MemberTypeUpdatePage();
    expect(await memberTypeUpdatePage.getPageTitle()).to.eq('Create or edit a Member Type');
    await memberTypeUpdatePage.cancel();
  });

  it('should create and save MemberTypes', async () => {
    const nbButtonsBeforeCreate = await memberTypeComponentsPage.countDeleteButtons();

    await memberTypeComponentsPage.clickOnCreateButton();

    await promise.all([
      memberTypeUpdatePage.setMemberTypeInput('memberType'),
      memberTypeUpdatePage.setDescriptionInput('description'),
      memberTypeUpdatePage.setImageUrlInput('imageUrl'),
    ]);

    await memberTypeUpdatePage.save();
    expect(await memberTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await memberTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last MemberType', async () => {
    const nbButtonsBeforeDelete = await memberTypeComponentsPage.countDeleteButtons();
    await memberTypeComponentsPage.clickOnLastDeleteButton();

    memberTypeDeleteDialog = new MemberTypeDeleteDialog();
    expect(await memberTypeDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Member Type?');
    await memberTypeDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(memberTypeComponentsPage.title), 5000);

    expect(await memberTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
