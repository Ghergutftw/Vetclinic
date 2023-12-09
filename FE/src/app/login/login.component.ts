import { Component } from '@angular/core';
import {Data, Router} from "@angular/router";
import {delay, timeout} from "rxjs";
import {AuthentificationService} from "../service/authentification.service";
import {DataService} from "../service/data.service";
import {User} from "../users/users.component";
import {LoginModel} from "../models/LoginModel";



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  ERROR_MESSAGE = "Wrong password or username!"
  // Delay de inchidere a popului wrong password (ms)
  POPUP_DELAY = 4000;
  username : string = ""
  password : string  = ""
  invalidLogin : boolean | unknown

  users !: User[]
  loginModel !: LoginModel;

  constructor( public router : Router ,
               public hardcodedAuthentificationService: AuthentificationService,
               public service:DataService
  ) {
  }

  handleLogin() {
      this.loginModel = new LoginModel(this.username, this.password)
      this.service.login(this.loginModel);


      if (this.hardcodedAuthentificationService.authenticate(this.loginModel)) {
        this.invalidLogin = false;
        this.router.navigate(['/welcome'])
      } else {
        this.invalidLogin = true;
        this.changeInvalidLogin()
      }
  }

  changeInvalidLogin(){
    setTimeout(() =>{
      this.invalidLogin = false;
    },this.POPUP_DELAY)
  }
  changeInvalidLoginInstantly(){
    setTimeout(() =>{
      this.invalidLogin = false;
    },0)
  }
}
