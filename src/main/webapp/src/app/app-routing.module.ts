import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AuthGuardsService} from "./guard/auth-guards.service";
import {PharmacyOwnerGuardService} from "./guard/pharmacy-owner-guard.service";
import {NoPharmacyOwnerGuardServiceService} from "./guard/no-pharmacy-owner-guard-service.service";

const routes: Routes = [
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
  {
    path: 'home',
    loadChildren: './home/home.module#HomeModule'
  },
  {
    path: 'terms',
    loadChildren: './terms/terms.module#TermsModule'
  },
  {
    path: 'accountSettings',
    loadChildren: './account-settings/account-settings.module#AccountSettingsModule',
    canActivate: [AuthGuardsService],
  },
  {
    path: 'search/book',
    loadChildren: './search-book/search.module#SearchModule',

  },
  {
    path: 'addLibrary',
    loadChildren: './add-library/add-library.module#AddLibraryModule',
    canActivate: [PharmacyOwnerGuardService, AuthGuardsService]
  },
  {
    path: 'myLibrary',
    loadChildren: './my-library/my-library.module#MyLibraryModule',
    canActivate: [PharmacyOwnerGuardService, AuthGuardsService]
  },
  {
    path: 'myReserv',
    loadChildren: './my-reserv-book/my-reserv-book.module#MyReservBookModule',
    canActivate: [NoPharmacyOwnerGuardServiceService, AuthGuardsService]
  },
  {
    path: 'reserv',
    loadChildren: './reserv/reserv.module#ReservModule',
    canActivate: [PharmacyOwnerGuardService, AuthGuardsService]

  },
  {
    path: 'user/:id',
    loadChildren: './user-details/user-details.module#UserDetailsModule',
    canActivate: [PharmacyOwnerGuardService, AuthGuardsService]

  },
  {
    path: "booked/book",
    loadChildren: './booked-book/booked-book.module#BookedBookModule'
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
