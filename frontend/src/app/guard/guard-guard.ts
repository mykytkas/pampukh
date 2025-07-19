import {CanActivateFn, RedirectCommand, Router} from '@angular/router';
import {inject} from '@angular/core';
import {AuthService} from '../services/auth-service';

export const guardGuard: CanActivateFn = (route, state) => {
  console.log("in guard!!")
  if (inject(AuthService).isLoggedIn()) {
    return true;
  }
  else {
    console.log("guard > not logged in!")
    const loginPath = inject(Router).parseUrl("/login")
    return loginPath
  }
};
