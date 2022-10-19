import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'member-type',
        data: { pageTitle: 'MemberTypes' },
        loadChildren: () => import('./member-type/member-type.module').then(m => m.MemberTypeModule),
      },
      {
        path: 'deal-type',
        data: { pageTitle: 'DealTypes' },
        loadChildren: () => import('./deal-type/deal-type.module').then(m => m.DealTypeModule),
      },
      {
        path: 'login-profile',
        data: { pageTitle: 'LoginProfiles' },
        loadChildren: () => import('./login-profile/login-profile.module').then(m => m.LoginProfileModule),
      },
      {
        path: 'bio-profile',
        data: { pageTitle: 'BioProfiles' },
        loadChildren: () => import('./bio-profile/bio-profile.module').then(m => m.BioProfileModule),
      },
      {
        path: 'deal',
        data: { pageTitle: 'Deals' },
        loadChildren: () => import('./deal/deal.module').then(m => m.DealModule),
      },
      {
        path: 'merchant',
        data: { pageTitle: 'Merchants' },
        loadChildren: () => import('./merchant/merchant.module').then(m => m.MerchantModule),
      },
      {
        path: 'category',
        data: { pageTitle: 'Categories' },
        loadChildren: () => import('./category/category.module').then(m => m.CategoryModule),
      },
      {
        path: 'email-subscription',
        data: { pageTitle: 'EmailSubscriptions' },
        loadChildren: () => import('./email-subscription/email-subscription.module').then(m => m.EmailSubscriptionModule),
      },
      {
        path: 'notification-type',
        data: { pageTitle: 'NotificationTypes' },
        loadChildren: () => import('./notification-type/notification-type.module').then(m => m.NotificationTypeModule),
      },
      {
        path: 'notification',
        data: { pageTitle: 'Notifications' },
        loadChildren: () => import('./notification/notification.module').then(m => m.NotificationModule),
      },
      {
        path: 'category-type',
        data: { pageTitle: 'CategoryTypes' },
        loadChildren: () => import('./category-type/category-type.module').then(m => m.CategoryTypeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
