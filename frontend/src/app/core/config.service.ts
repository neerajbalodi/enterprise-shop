import { Injectable } from '@angular/core';

export interface AppConfig { apiBaseUrl: string; }

/** Holds runtime config loaded from assets/config.json — never baked into the bundle. */
@Injectable({ providedIn: 'root' })
export class ConfigService {
  private config: AppConfig = { apiBaseUrl: '/api' };

  async load(): Promise<void> {
    try {
      const res = await fetch('assets/config.json', { cache: 'no-store' });
      if (res.ok) { this.config = await res.json(); }
    } catch {
      // fall back to the default apiBaseUrl
    }
  }

  get apiBaseUrl(): string { return this.config.apiBaseUrl; }
}
