import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Global} from '../globals/global';
import {UserAuth} from '../dto/userDto';
import {map, Observable, tap} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private backendUrl ;

  constructor(private httpClient: HttpClient, private global: Global) {
    this.backendUrl = this.global.backendUrl;
  }

  register(userData: UserAuth): Observable<string>{

    return this.httpClient.post<{ token: string }>(`${this.backendUrl}/register`, userData)
      .pipe(map(tokenJson => tokenJson.token))
      .pipe(tap({
        next: value => this.setToken(value)
      }));
  }

  getToken(){
    return localStorage.getItem("authToken");
  }
  setToken(token: string){
    localStorage.setItem("authToken", token)
  }

  login(userData: UserAuth) {
    console.log(userData)
    return this.httpClient.post<{ token: string }>(`${this.backendUrl}/login`, userData)
      .pipe(map(tokenJson => tokenJson.token))
      .pipe(tap({
      next: value => this.setToken(value),
      error: err => console.log(err)
    }))
  }

  isLoggedIn() {
    // ihatedoublenegation.jpg
    return !! localStorage.getItem("authToken")
  }


}
