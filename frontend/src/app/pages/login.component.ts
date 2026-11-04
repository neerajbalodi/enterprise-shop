import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../core/api.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <h2>Login</h2>
    <div class="card">
      <label>Email</label>
      <input [(ngModel)]="email" type="email" placeholder="you@example.com">
      <label>Password</label>
      <input [(ngModel)]="password" type="password">
      <button (click)="submit()">Sign in</button>
      <p *ngIf="message">{{ message }}</p>
    </div>
  `
})
export class LoginComponent {
  email = '';
  password = '';
  message = '';
  constructor(private api: ApiService) {}
  submit(): void {
    this.api.login(this.email, this.password).subscribe({
      next: r => this.message = 'Signed in as ' + r.email,
      error: e => this.message = 'Login failed: ' + (e.error?.message ?? e.message)
    });
  }
}
