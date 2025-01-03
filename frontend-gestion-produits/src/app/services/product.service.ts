import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private readonly httpClient = inject(HttpClient);

  private readonly url = environment.BACKEND_URL + 'products';

  constructor() {}

  getProducts(): Observable<Product[]> {
    return this.httpClient.get<Product[]>(this.url);
  }
  getProductById(id: number): Observable<Product> {
    return this.httpClient.get<Product>(`${this.url}/${id}`);
  }
}
