import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ConfigService } from './config.service';

export interface Product { id: string; name: string; price: number; stock: number; }
export interface AuthResponse { token: string; userId: string; email: string; }

@Injectable({ providedIn: 'root' })
export class ApiService {
  constructor(private http: HttpClient, private config: ConfigService) {}

  private base() { return this.config.apiBaseUrl; }

  login(email: string, password: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.base()}/auth/login`, { email, password });
  }

  register(email: string, password: string, displayName: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.base()}/auth/register`, { email, password, displayName });
  }

  products(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.base()}/products`);
  }
}
