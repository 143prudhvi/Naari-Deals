import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BioProfileComponentsPage, BioProfileDeleteDialog, BioProfileUpdatePage } from './bio-profile.page-object';

const expect = chai.expect;

describe('BioProfile e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let bioProfileComponentsPage: BioProfileComponentsPage;
  let bioProfileUpdatePage: BioProfileUpdatePage;
  let bioProfileDeleteDialog: BioProfileDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load BioProfiles', async () => {
    await navBarPage.goToEntity('bio-profile');
    bioProfileComponentsPage = new BioProfileComponentsPage();
    await browser.wait(ec.visibilityOf(bioProfileComponentsPage.title), 5000);
    expect(await bioProfileComponentsPage.getTitle()).to.eq('Bio Profiles');
    await browser.wait(ec.or(ec.visibilityOf(bioProfileComponentsPage.entities), ec.visibilityOf(bioProfileComponentsPage.noResult)), 1000);
  });

  it('should load create BioProfile page', async () => {
    await bioProfileComponentsPage.clickOnCreateButton();
    bioProfileUpdatePage = new BioProfileUpdatePage();
    expect(await bioProfileUpdatePage.getPageTitle()).to.eq('Create or edit a Bio Profile');
    await bioProfileUpdatePage.cancel();
  });

  it('should create and save BioProfiles', async () => {
    const nbButtonsBeforeCreate = await bioProfileComponentsPage.countDeleteButtons();

    await bioProfileComponentsPage.clickOnCreateButton();

    await promise.all([
      bioProfileUpdatePage.setUserIdInput('userId'),
      bioProfileUpdatePage.setFirstNameInput('firstName'),
      bioProfileUpdatePage.setLastNameInput('lastName'),
      bioProfileUpdatePage.setDobInput('dob'),
      bioProfileUpdatePage.setGenderInput('gender'),
      bioProfileUpdatePage.setImageUrlInput('imageUrl'),
    ]);

    await bioProfileUpdatePage.save();
    expect(await bioProfileUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await bioProfileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last BioProfile', async () => {
    const nbButtonsBeforeDelete = await bioProfileComponentsPage.countDeleteButtons();
    await bioProfileComponentsPage.clickOnLastDeleteButton();

    bioProfileDeleteDialog = new BioProfileDeleteDialog();
    expect(await bioProfileDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Bio Profile?');
    await bioProfileDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(bioProfileComponentsPage.title), 5000);

    expect(await bioProfileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
