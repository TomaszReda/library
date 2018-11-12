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
    path: 'users',
    loadChildren: './user/user.module#UserModule'
  },
  {
    path: 'search',
    loadChildren: './search/search.module#SearchModule',
    canActivate: [AuthGuardsService],
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
