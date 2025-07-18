import { HttpInterceptorFn } from '@angular/common/http';
import {AuthService} from '../services/auth-service';
import {inject} from '@angular/core';
import {Global} from '../globals/global';

export const inteceptorInterceptor: HttpInterceptorFn = (req, next) => {

  const global = inject(Global)
  const ignore: string[] = [
    `${global.backendUrl}/register`,
    `${global.backendUrl}/login`];

  if (ignore.includes(req.url)) {
    return next(req);
  }

  const authService = inject(AuthService)
  if (!authService.isLoggedIn()) return next(req);

  const withToken = req.clone({
    headers : req.headers.set("Authorization", authService.getToken()!)
  })
  return next(withToken);
};
