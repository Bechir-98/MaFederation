import { HttpRequest, HttpHandlerFn, HttpEvent } from '@angular/common/http';
import { authInterceptor } from './auth-interceptor';
import { of } from 'rxjs';

describe('authInterceptor', () => {
  it('should attach Authorization header if token exists', (done) => {
    const token = 'fake-jwt-token';
    localStorage.setItem('token', token);

    const req = new HttpRequest('GET', '/test');
    const next: HttpHandlerFn = (r: HttpRequest<any>) => {
      expect(r.headers.get('Authorization')).toBe(`Bearer ${token}`);
      return of({} as HttpEvent<any>);
    };

    authInterceptor(req, next).subscribe(() => {
      done();
    });

    localStorage.removeItem('token');
  });

  it('should not add Authorization header if token is missing', (done) => {
    localStorage.removeItem('token');

    const req = new HttpRequest('GET', '/test');
    const next: HttpHandlerFn = (r: HttpRequest<any>) => {
      expect(r.headers.get('Authorization')).toBeNull();
      return of({} as HttpEvent<any>);
    };

    authInterceptor(req, next).subscribe(() => {
      done();
    });
  });
});
