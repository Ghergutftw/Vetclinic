// signup.component.ts

import {Component, OnInit} from '@angular/core';
import {User} from "../../models/User";
import {DataService} from "../../service/data.service";
import {Router} from "@angular/router";
import {NotifierService} from "angular-notifier";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  user: User = new User();

  constructor(
    public dataService: DataService,
    public router: Router,
    public notifier: NotifierService
  ) {
  }

  handleSignUp() {
    this.dataService.createUser(this.user).subscribe(
      response => {
        if(response.status == "success"){
          this.notifier.notify("success", "User created successfully!")
        }
        this.router.navigate(['login'])
      }
    )

  }
}
