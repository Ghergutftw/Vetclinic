import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {DataService} from "../../service/data.service";
import {User} from "../../models/User";

@Component({
  selector: 'app-update',
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
        this.user = response;
        this.service.getDecodedString(this.user.password).subscribe(
          responseDecoded=>{
            this.user.password = responseDecoded
          }
        )
      }
    )

  }

  updateUser(id:number) {
    this.service.updateUserById(id,this.user).subscribe(
      ()=>{
        this.router.navigate(["/users"])
      }
    )
  }

}
