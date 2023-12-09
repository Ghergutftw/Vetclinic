import {Injectable} from '@angular/core';
import {DataService} from "./data.service";
import {User} from "../users/users.component";
import {LoginModel} from "../models/LoginModel";
import {LoginResponse} from "../models/LoginResponse";

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

  authenticate(loginModel: LoginModel) {
    this.service.login(loginModel).subscribe(
      (response: LoginResponse) => {
        if (response.status == "success") {
          sessionStorage.setItem("Authenticated User", loginModel.email);
          this.canLogin = true;
        }
      })

    return this.canLogin;
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem("Authenticated User")
    return !(user === null);
  }

  logOut() {
    sessionStorage.removeItem("Authenticated User")
  }

}
