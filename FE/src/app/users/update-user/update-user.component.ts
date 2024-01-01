import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {DataService} from "../../service/data.service";
import {User} from "../../models/User";
import {Roles} from "../users.component";

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent implements OnInit{

  id!: number

  decodedPassword!:string;

  user!: User
  constructor(
    public route: ActivatedRoute,
    public service:DataService,
    public router:Router
  ) {
  }

  ngOnInit(): void {
    this.user = new User();
    this.id = this.route.snapshot.params["id"]
    this.service.getUserById(this.id).subscribe(
      response=>{
        console.log(response)
        this.user = response;
        this.service.getDecodedString(this.user.password).subscribe(
          responseDecoded=>{
            console.log(responseDecoded);
            this.user.password = responseDecoded
          }
        )
      }
    )

  }

  updateUser(id:number) {
    console.log("updating")
    this.service.updateUserById(id,this.user).subscribe(
      ()=>{
        this.router.navigate(["/users-list"])
      }
    )
  }

}
