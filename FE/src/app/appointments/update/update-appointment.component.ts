import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {DataService} from "../../service/data.service";
import {Appointment} from "../../models/Appointment";
import {Doctor} from "../../models/Doctor";

@Component({
  selector: 'app-update',
  templateUrl: './update-appointment.component.html',
  styleUrls: ['./update-appointment.component.css']
})
export class UpdateAppointmentComponent implements OnInit{

  appointment: Appointment = new Appointment();
  doctors: Doctor[] = [];
  id !: number;

  constructor(
    private service: DataService,
    private router: Router,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.service.getAppointmentById(this.id).subscribe(
      response => {
        this.appointment = response;
        this.service.getDoctorById(this.appointment.doctorId).subscribe(
          response => this.appointment.doctorLastName = response.lastName
        )
      }
    )

    this.service.getAllDoctors().subscribe(
      response => this.doctors = response
    )
  }

  updateAppointment() {
    this.service.updateAppointment(this.id, this.appointment).subscribe(
      response => {
        this.router.navigate(['appointments'])
      }
    )
  }

}
