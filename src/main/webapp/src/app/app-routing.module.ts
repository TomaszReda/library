import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
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
    canActivate: [ AuthGuardsService],
    canLoad: [LibraryOwnerGuardService]
    
  },
  {
    path: 'myLibrary',
    loadChildren: './forLibraryOwner/my-library/my-library.module#MyLibraryModule',
    canActivate: [AuthGuardsService],
    canLoad: [LibraryOwnerGuardService]
  },
  {
    path: 'myReserv',
    loadChildren: './forCasualUser/my-reserv-book/my-reserv-book.module#MyReservBookModule',
    canActivate: [AuthGuardsService],
    canLoad: [CasualUserGuardService]
  },
  {
    path: 'reserv',
    loadChildren: './forLibraryOwner/reserv/reserv.module#ReservModule',
    canActivate: [AuthGuardsService],
    canLoad: [LibraryOwnerGuardService]

  },
  {
    path: 'user/:id',
    loadChildren: './forLibraryOwnerAndAdmin/user-details/user-details.module#UserDetailsModule',
    canActivate: [AuthGuardsService],
    canLoad: [LibraryOwnerAndAdminGuardService]
  },
  {
    path: "booked/book",
    loadChildren: './forCasualUser/booked-book/booked-book.module#BookedBookModule',
    canActivate: [AuthGuardsService],
    canLoad: [CasualUserGuardService]

  },
  {
    path: "return/book",
    loadChildren: "./forLibraryOwner/return-book/return-book.module#ReturnBookModule",
    canActivate: [AuthGuardsService],
    canLoad: [LibraryOwnerGuardService]
  },
  {
    path: "admin/panel",
    loadChildren: "./forAdmin/admin-panel/admin-panel.module#AdminPanelModule",
    canActivate: [AuthGuardsService],
    canLoad: [AdminGuardService]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
