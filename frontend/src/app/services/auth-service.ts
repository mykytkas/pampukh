import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Global} from '../globals/global';
import {UserAuth} from '../dto/userDto';
import {Observable, tap} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private backendUrl ;

  constructor(private httpClient: HttpClient, private global: Global) {
    this.backendUrl = this.global.backendUrl;
  }

  register(userData: UserAuth): Observable<string>{

    return this.httpClient.post<string>(`${this.backendUrl}/register`, userData)
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
    return this.httpClient.post<string>(`${this.backendUrl}/login`, userData).pipe(tap({
      next: value => this.setToken(value)
    }))
  }

  isLoggedIn() {
    // ihatedoublenegation.jpg
    return !! localStorage.getItem("authToken")
  }


}
