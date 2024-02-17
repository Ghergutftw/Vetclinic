import {Component} from '@angular/core';
import {User} from "../../models/User";
import {DataService} from "../../service/data.service";
import {Router} from "@angular/router";
import {NotificationService} from "../../service/notification.service";

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
    private notSer: NotificationService
  ) {
  }

  handleSignUp() {
    this.dataService.createUser(this.user).subscribe(
      response => {
        if (response.status == "success") {
          this.notSer.showNotification("User created successfully")
          this.router.navigate(['login'])
        } else if (response.status == "failed") {
          this.notSer.showNotification("User already exists!")
        }
      }
    )

  }
}
