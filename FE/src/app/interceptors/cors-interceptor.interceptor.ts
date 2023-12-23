import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class CorsInterceptor implements HttpInterceptor {
  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    // Clone the request and set necessary headers for CORS
    const modifiedRequest = request.clone({
      setHeaders: {
        'Access-Control-Allow-Origin': 'http://localhost:4200',
      },
    });
    return next.handle(modifiedRequest);
  }
}
