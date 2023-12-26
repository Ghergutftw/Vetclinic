import { Component } from '@angular/core';
import {AuthentificationService} from "../service/authentification.service";

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent {

  constructor(public hardcodedAuthentificationService : AuthentificationService) {
  }

  ngOnInit(): void{
    this.hardcodedAuthentificationService.logOut()
    sessionStorage.clear();
  }

}
