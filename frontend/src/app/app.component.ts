import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  template: `
    <div class="container">
      <h1>Enterprise Shop</h1>
      <nav>
        <a routerLink="/products">Products</a>
        <a routerLink="/login">Login</a>
      </nav>
      <router-outlet></router-outlet>
    </div>
  `
})
export class AppComponent {}
