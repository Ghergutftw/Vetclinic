import {Component, OnInit} from '@angular/core';
import {DataService} from "../../service/data.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Doctor} from "../../models/Doctor";
import {User} from "../../models/User";
import {Roles} from "../../users/users.component";

@Component({
  selector: 'app-update-doctor',
  templateUrl: './update-doctor.component.html',
  styleUrls: ['./update-doctor.component.css']
})
export class UpdateDoctorComponent implements OnInit{

  doctor!: Doctor;

  user!: User;
  id!: number | any

  constructor(
    private service:DataService,
    private route:ActivatedRoute,
    private router:Router
  ) {
  }

  ngOnInit(){
    this.user = new User("", "" ,"", Roles.DEFAULT)
    this.doctor = new Doctor(0, "", "", "", 0, 0, this.user)
    this.id=this.route.snapshot.params['id']
    this.service.retrieveDoctorById(this.id).subscribe(
      (response:Doctor) =>{
        this.doctor = response
        this.doctor.user = response.user;
      }
    )
  }
  updateDoctor(id:number) {
    this.service.updateDoctorById(id,this.doctor).subscribe(
      () =>{
        this.router.navigate(["/doctors-list"])
      }
    )

  }
}
