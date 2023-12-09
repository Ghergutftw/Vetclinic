import {Component} from '@angular/core';
import {DataService} from "../../service/data.service";
import {Doctor} from "../doctor-list.component";
import {ActivatedRoute, Router} from "@angular/router";
import {Roles, User} from "../../users/users.component";

@Component({
  selector: 'app-update-doctor',
  templateUrl: './update-doctor.component.html',
  styleUrls: ['./update-doctor.component.css']
})
export class UpdateDoctorComponent {

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
    this.user = new User("", "" ,"", Roles.DOCTOR)
    this.doctor = new Doctor(0, "", "", "", 0, 0, this.user)
    this.id=this.route.snapshot.params['id']
    console.log("Din init de la update 1")
    this.service.retrieveDoctorById(this.id).subscribe(
      (response:Doctor) =>{
            console.log(response)
            this.doctor = new Doctor(response.id, response.firstName, response.lastName, response.speciality, response.age, response.yearsOfExperience, response.user)
      }
    )
    console.log("Din init de la update 2")
  }
  updateDoctor(id:number) {
    console.log("UPDATING THE DOCTOR")
    this.service.updateDoctorById(id,this.doctor).subscribe(
      response =>{
        console.log(response)
        this.router.navigate(["/doctors-list"])
      }
    )

  }
}
