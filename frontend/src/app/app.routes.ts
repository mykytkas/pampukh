import { Routes } from '@angular/router';
import {User} from './user/user';
import {guardGuard} from './guard/guard-guard';

export const routes: Routes = [
  {path:"login", component: User},
  {path:"**", component: User, canActivate: [guardGuard]},

];
