import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService, Product } from '../core/api.service';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule],
  template: `
    <h2>Catalog</h2>
    <div class="card" *ngFor="let p of products">
      <strong>{{ p.name }}</strong> — {{ p.price | currency }}
      <span> · {{ p.stock }} in stock</span>
    </div>
    <p *ngIf="error" style="color:#b00">Could not load products: {{ error }}</p>
  `
})
export class ProductsComponent implements OnInit {
  products: Product[] = [];
  error = '';
  constructor(private api: ApiService) {}
  ngOnInit(): void {
    this.api.products().subscribe({
      next: p => this.products = p,
      error: e => this.error = e.message ?? 'request failed'
    });
  }
}
