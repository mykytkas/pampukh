import { Routes } from '@angular/router';
import {User} from './components/user/user';
import {guardGuard} from './guard/guard-guard';
import {Login} from './components/login/login';
import {Register} from './components/register/register';

export const routes: Routes = [
  {path:"login", component: Login},
  {path:"register", component: Register},
  {path:"user", component: User, canActivate: [guardGuard]},
  {path:"**", component: Login, canActivate: [guardGuard]},

];
