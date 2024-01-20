import {Injectable} from '@angular/core';
import {DataService} from "./data.service";
import {Login} from "../models/Login";
import {LoginResponse} from "../models/LoginResponse";
import {map, Observable} from "rxjs";
import {User} from "../models/User";

@Injectable({
  providedIn: 'root'
})
export class AuthentificationService {
  constructor(
    public service: DataService
  ) {
  }

  users !: User[];
  canLogin!: boolean;

  authenticate(loginModel: Login): Observable<boolean> {
    return this.service.login(loginModel).pipe(
       map((response: LoginResponse) => {
        if (response.status === "success") {
          sessionStorage.setItem("Authenticated User", loginModel.email);
          return true;
        }
        return false;
      })
    );
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem("Authenticated User")
    return !(user === null);
  }

  logOut() {
    sessionStorage.removeItem("Authenticated User")
    this.service.logout();
  }

}
