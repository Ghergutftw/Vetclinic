import {Component, OnInit} from '@angular/core';
import {DataService} from "../service/data.service";
import {Appointment} from "../models/Appointment";
import {Router} from "@angular/router";
import {Status} from "../Enums/Status";
import {NotificationService} from "../service/notification.service";

@Component({
  selector: 'app-appointments',
  templateUrl: './appointments.component.html',
  styleUrls: ['./appointments.component.css']
})

export class AppointmentsComponent implements OnInit {
  appointments: Appointment[] = []
  deleteMessage: string = ""

  constructor(
    public dataService: DataService,
    public router: Router,
    private notService : NotificationService
  ) {
  }

  ngOnInit() {
    this.getAppointments();
  }

  updateAppointment(id: number | undefined) {
    this.router.navigate(["update-appointment", id])
  }

  deleteAppointment(id: number | undefined) {
    this.dataService.deleteAppointment(id).subscribe(
      () => {
        this.notService.showNotification("Appointment deleted successfully!");
        this.getAppointments();
      }
    )
  }

  getAppointments() {
    this.appointments = [];
    this.dataService.getAllAppointments().subscribe(
      response => {
        response.map(appointment => {
          // if(appointment.status != Status.FINISHED){
          //   this.appointments.push(appointment)
          // }
          this.appointments.push(appointment)
        })
      }
    )
  }

  createAppointment() {
    this.router.navigate(["create-appointment"])
  }

  processAppointment(id: number | undefined, doctorLastName: string | undefined) {
    this.dataService.updateStatus(id,Status.IN_PROGRESS).subscribe(
      () => {
        this.router.navigate(["create-consultation"],{queryParams: {id: id , lastName: doctorLastName}})
      }
    )
  }
}
