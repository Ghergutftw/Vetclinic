import {Component} from '@angular/core';
import {DataService} from "../../service/data.service";
import {Router} from "@angular/router";
import {Roles} from "../../users/users.component";
import {AlertService} from "../../alert";
import {Doctor} from "../../models/Doctor";
import {User} from "../../models/User";

@Component({
  selector: 'app-create',
  templateUrl: './create-doctor.component.html',
  styleUrls: ['./create-doctor.component.css']
})
export class CreateDoctorComponent {
  createdDoctor!: Doctor

  user!: User

  constructor(
    private service : DataService,
    private router: Router,
    private alertService:AlertService
  ) {

  }

  ngOnInit(){
    this.user = new User()
    this.createdDoctor = new Doctor(0, "", "", "", 0, 0, this.user )
  }


  createDoctor(doctor : Doctor) {
    this.createdDoctor.firstName = doctor.firstName
    this.createdDoctor.lastName = doctor.lastName
    this.createdDoctor.speciality = doctor.speciality
    this.createdDoctor.age = doctor.age
    this.createdDoctor.yearsOfExperience = doctor.yearsOfExperience
    this.createdDoctor.user.email = doctor.user.email;
    this.createdDoctor.user.password=doctor.user.password;
    this.createdDoctor.user.role = doctor.user.role;
    this.createdDoctor.user.username = doctor.user.username;

    this.service.createDoctor(this.createdDoctor).subscribe(() =>{
      this.alertService.created("CREATED")
      this.router.navigate(["/doctors"])
    })

  }
}
