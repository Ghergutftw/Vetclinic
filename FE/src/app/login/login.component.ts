import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {AuthentificationService} from "../service/authentification.service";
import {DataService} from "../service/data.service";
import {LoginModel} from "../models/LoginModel";
import {User} from "../models/User";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  ERROR_MESSAGE = "Wrong password or username!"
  // Delay de inchidere a popului wrong password (ms)
  POPUP_DELAY = 4000;
  username: string = ""
  password: string = ""
  invalidLogin: boolean | unknown

  users !: User[]
  loginModel !: LoginModel;

  constructor(public router: Router,
              public authService: AuthentificationService,
              public service: DataService
  ) {
  }

  handleLogin() {
    this.loginModel = new LoginModel(this.username, this.password);

    this.authService.authenticate(this.loginModel)
      .subscribe((authenticated: boolean) => {
        if (authenticated) {
          this.invalidLogin = false;
          this.router.navigate(['/welcome']).then(r => console.log(r));
        } else {
          this.invalidLogin = true;
          this.changeInvalidLogin();
        }
      });
  }

  changeInvalidLogin() {
    setTimeout(() => {
      this.invalidLogin = false;
    }, this.POPUP_DELAY)
  }

  changeInvalidLoginInstantly() {
    setTimeout(() => {
      this.invalidLogin = false;
    }, 0)
  }

}
