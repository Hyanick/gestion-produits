import { Routes } from '@angular/router';
import { ProductDetailComponent } from './pages/products/product-detail/product-detail.component';
import { ProductListComponent } from './pages/products/product-list/product-list.component';

export const routes: Routes = [
  { path: 'products', component: ProductListComponent },
  { path: 'products/:id', component: ProductDetailComponent },
  { path: '**', redirectTo: 'products' },
];
