import {CanActivateFn, RedirectCommand, Router} from '@angular/router';
import {inject} from '@angular/core';
import {AuthService} from '../services/auth-service';

export const guardGuard: CanActivateFn = (route, state) => {
  if (inject(AuthService).isLoggedIn()) {
    return true;
  }
  else {
    const loginPath = inject(Router).parseUrl("/login")
    return new RedirectCommand(loginPath, {skipLocationChange: false})
  }
};
