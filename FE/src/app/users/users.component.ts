import { Component } from '@angular/core';
import {DataService} from "../service/data.service";
import {Router} from "@angular/router";
import {logMessages} from "@angular-devkit/build-angular/src/builders/browser-esbuild/esbuild";


export enum Roles{
  ADMIN = "ADMIN",
  DOCTOR = "DOCTOR",
  DEFAULT = ""
}
export class User{
  constructor(
    public email : string,
    public username : string,
    public password : string,
    public role : Roles
  ) {
  }

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
    this.dataService.getAllUsers().subscribe(
      response =>{
        console.log(response)
        this.users = response
      }
    )
  }

  updateUser(id : number) {
    console.log("UPDATING USER")
    this.router.navigate(["update-user" , id])
  }

  decrypt() {
    console.log("Decrypting password");
    for (let i = 0; i < this.users.length; i++) {
      this.dataService.getDecodedString(this.users[i].password).subscribe(
        value => {
          console.log(value)
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
    console.log("Encrypting password");
    for (let i = 0; i < this.users.length; i++) {
      this.dataService.getEncodedString(this.users[i].password).subscribe(
        value => {
          console.log(value)
          this.users[i].password = value
        }
      )
    }
  }
}
