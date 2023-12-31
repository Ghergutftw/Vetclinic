import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthentificationService} from "../service/authentification.service";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit , OnDestroy{
  currentUser: string | any = "";

  constructor(public authService : AuthentificationService) {
  }

  ngOnInit(): void {
    this.currentUser = sessionStorage.getItem("Authenticated User");
  }

  ngOnDestroy(): void {
    this.currentUser = "";
  }

  protected readonly sessionStorage = sessionStorage;
}
