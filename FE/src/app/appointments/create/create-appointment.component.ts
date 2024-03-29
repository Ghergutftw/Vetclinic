import {Component, OnInit} from '@angular/core';
import {DataService} from "../../service/data.service";
import {Appointment} from "../../models/Appointment";
import {Router} from "@angular/router";
import {Doctor} from "../../models/Doctor";
import {Owner} from "../../models/Owner";
import {NotificationService} from "../../service/notification.service";

@Component({
  selector: 'app-create',
  templateUrl: './create-appointment.component.html',
  styleUrls: ['./create-appointment.component.css']
})
export class CreateAppointmentComponent implements OnInit {

  appointment : Appointment = new Appointment();
  doctors !: Doctor[]
  constructor(
    private service: DataService,
    private router: Router,
    private notService: NotificationService
  ) {
  }

  ngOnInit(): void {
    this.appointment = new Appointment();
    this.appointment.owner = new Owner();

    this.service.getAllDoctors().subscribe(
      response => this.doctors = response
    )

  }
  createAppointment() {
    this.service.createAppointment(this.appointment).subscribe(
      () => {
        this.notService.showNotification("Appointment created successfully");
        this.router.navigate(['appointments'])
      }
    )
  }
}
