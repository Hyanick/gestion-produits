import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '../../../services/product.service';
import { Product } from '../../../models/product';
import { CommonModule } from '@angular/common';
import { SlickCarouselModule } from 'ngx-slick-carousel';

@Component({
  selector: 'app-product-detail',
  imports: [CommonModule, SlickCarouselModule],
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.scss',
})
export class ProductDetailComponent implements OnInit {
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly productService = inject(ProductService);
  product!: Product;

  selectedImage: string | null = null;

  urlLocalImages = ['C:/Devs/Projets_Perso/images/', 'http://192.168.1.23:3001/']
  ngOnInit(): void {
    const productId = this.activatedRoute.snapshot?.paramMap?.get('id');
    if (productId) {
      this.productService.getProductById(+productId).subscribe((data) => {
        this.product = data;
      });
    }
  }

  setSelectedImage(imageUrl: string): void {
    this.selectedImage = imageUrl;
  }
}
