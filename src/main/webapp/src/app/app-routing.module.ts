import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {HomeModule} from './home/home.module';
import {AuthGuardsService} from "./guard/auth-guards.service";

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
    path: 'search',
    loadChildren: './search/search.module#SearchModule',
    canActivate: [AuthGuardsService],
  },
  {
    path: 'addLibrary',
    loadChildren: './add-library/add-library.module#AddLibraryModule'
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
