import { Component } from '@angular/core';
import {DataService} from "../service/data.service";
import {Router} from "@angular/router";
import {User} from "../models/User";

export enum Roles{
  ADMIN = "ADMIN",
  DOCTOR = "DOCTOR",
  DEFAULT = ""
}
@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent {

  users : User[] | any

  numberOfPresses : number = 0;

  constructor(public dataService:DataService,
              public router:Router) {
  }

  ngOnInit(){
    this.refreshPage()
    this.numberOfPresses = 0 ;
  }

  refreshPage(){
    this.users = [];
    this.dataService.getAllUsers().subscribe(
      response =>{
        this.users = response
      }
    )
  }

  updateUser(id : number) {
    this.router.navigate(["update-user" , id])
  }

  decrypt() {
    for (let i = 0; i < this.users.length; i++) {
      this.dataService.getDecodedString(this.users[i].password).subscribe(
        value => {
          this.users[i].password = value
        }
      )
    }

  }

  handlePassword(){
    this.numberOfPresses ++;
    if(this.numberOfPresses % 2 === 1){
      this.decrypt()
    }else{
      this.encrypt()
    }
  }
  encrypt() {
    for (let i = 0; i < this.users.length; i++) {
      this.dataService.getEncodedString(this.users[i].password).subscribe(
        value => {
          this.users[i].password = value
        }
      )
    }
  }
}
