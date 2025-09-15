import 'zone.js'; // required for change detection
import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { authInterceptor } from './app/services/auth-interceptor/auth-interceptor';
import { importProvidersFrom } from '@angular/core';
import { RouterModule } from '@angular/router';
import { routes } from './app/app.routes';

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(withInterceptors([authInterceptor])),
    importProvidersFrom(RouterModule.forRoot(routes)) // <-- must provide router
  ]
}).catch(err => console.error(err));
