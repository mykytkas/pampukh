import { Routes } from '@angular/router';
import {User} from './user/user';
import {guardGuard} from './guard/guard-guard';
import {App} from './app';

export const routes: Routes = [
  {path: "login", component: User, canActivate: [guardGuard]},
  {path: "**", component: App, canActivate: [guardGuard]},
];
