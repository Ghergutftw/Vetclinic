import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {AuthentificationService} from "../service/authentification.service";
import {DataService} from "../service/data.service";
import {Login} from "../models/Login";
import {User} from "../models/User";
import {NotificationService} from "../service/notification.service";

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
  loginModel !: Login;

  constructor(public router: Router,
              public authService: AuthentificationService,
              public service: DataService,
              private notificationService: NotificationService
  ) {
  }

  handleLogin() {
    this.loginModel = new Login(this.username, this.password);

    this.authService.authenticate(this.loginModel)
      .subscribe((authenticated: boolean) => {
        if (authenticated) {
          this.invalidLogin = false;
          this.notificationService.showNotification('Login successful');
          this.router.navigate(['/welcome']);
        } else {
          this.invalidLogin = true;
          this.notificationService.showNotification(this.ERROR_MESSAGE);
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

  showNotification(): void {
    this.notificationService.showNotification('Custom message here');
  }
}
