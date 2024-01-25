// signup.component.ts

import { Component } from '@angular/core';
import {User} from "../../models/User";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  user!: User// Assuming you have a user object
  recaptchaResponse: string = "";

  handleSignUp() {
    if (!this.recaptchaResponse) {
      console.error('reCAPTCHA not checked!');
      return;
    }


    this.recaptchaResponse = '';
  }
}
