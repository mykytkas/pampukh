import {Injectable} from '@angular/core';

@Injectable({providedIn: "root"})
export class Global {
  readonly backendUrl: string = "http://localhost:8080/pampukh";
}
