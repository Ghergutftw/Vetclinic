import { Component } from '@angular/core';
import {AuthentificationService} from "../service/authentification.service";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent {
  constructor(public hardcodedAuthentificationService : AuthentificationService) {
  }

}
