import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login.component';
import { ProductsComponent } from './pages/products.component';

export const routes: Routes = [
  { path: '', redirectTo: 'products', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'products', component: ProductsComponent }
];
