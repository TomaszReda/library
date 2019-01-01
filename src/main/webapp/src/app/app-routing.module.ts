import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuardsService} from "./guard/auth-guards.service";
import {LibraryOwnerGuardService} from "./guard/library-owner-guard.service";
import {CasualUserGuardService} from "./guard/casual-user-guard.service";
import {AdminGuardService} from "./guard/admin-guard.service";
import {LibraryOwnerAndAdminGuardService} from "./guard/library-owner-and-admin-guard.service";

const routes: Routes = [
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
  {
    path: 'home',
    loadChildren: './forAll/home/home.module#HomeModule'
  },
  {
    path: 'terms',
    loadChildren: './forAll//terms/terms.module#TermsModule'
  },
  {
    path: 'accountSettings',
    loadChildren: './forLoggedUser/account-settings/account-settings.module#AccountSettingsModule',
    canActivate: [AuthGuardsService],
  },
  {
    path: 'search/book',
    loadChildren: './forAll/search-book/search.module#SearchModule',
  },
  {
    path: 'addLibrary',
    loadChildren: './forLibraryOwner/add-library/add-library.module#AddLibraryModule',
    canActivate: [ AuthGuardsService,LibraryOwnerGuardService],

  },
  {
    path: 'myLibrary',
    loadChildren: './forLibraryOwner/my-library/my-library.module#MyLibraryModule',
    canActivate: [AuthGuardsService,LibraryOwnerGuardService],
  },
  {
    path: 'myReserv',
    loadChildren: './forCasualUser/my-reserv-book/my-reserv-book.module#MyReservBookModule',
    canActivate: [AuthGuardsService,CasualUserGuardService],
  },
  {
    path: 'reserv',
    loadChildren: './forLibraryOwner/reserv/reserv.module#ReservModule',
    canActivate: [AuthGuardsService,LibraryOwnerGuardService],

  },
  {
    path: 'user/:id',
    loadChildren: './forLibraryOwnerAndAdmin/user-details/user-details.module#UserDetailsModule',
    canActivate: [AuthGuardsService,LibraryOwnerAndAdminGuardService],
  },
  {
    path: "booked/book",
    loadChildren: './forCasualUser/booked-book/booked-book.module#BookedBookModule',
    canActivate: [AuthGuardsService,CasualUserGuardService],

  },
  {
    path: "return/book",
    loadChildren: "./forLibraryOwner/return-book/return-book.module#ReturnBookModule",
    canActivate: [AuthGuardsService,LibraryOwnerGuardService],
  },
  {
    path: "admin/panel",
    loadChildren: "./forAdmin/admin-panel/admin-panel.module#AdminPanelModule",
    canActivate: [AuthGuardsService,AdminGuardService],
  },
  {
    path: "library/:libraryId",
    loadChildren: "./forAdmin/library-details/library-details.module#LibraryDetailsModule",
    canActivate: [AuthGuardsService,AdminGuardService],
  },
  {
    path: "reset/password/:resetPasswordToken",
    loadChildren: "./forAll/reset-password/reset-password.module#ResetPasswordModule"
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
