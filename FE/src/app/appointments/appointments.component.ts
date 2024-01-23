import {Component, OnInit} from '@angular/core';
import {DataService} from "../service/data.service";
import {Appointment} from "../models/Appointment";
import {Router} from "@angular/router";
import { Status } from "../Enums/Status";

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
    public router: Router
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
      response => {
        this.deleteMessage = `Appointment ${id} was deleted successfully!`
        this.getAppointments();
      }
    )
  }

  getAppointments() {
    this.dataService.getAllAppointments().subscribe(
      response => {
        this.appointments = response;
      }
    )
  }

  createAppointment() {
    this.router.navigate(["create-appointment"])
  }

  processAppointment(id: number | undefined, doctorLastName: string | undefined) {
    this.dataService.updateStatus(id,Status.IN_PROGRESS).subscribe(
      response => {
        this.router.navigate(["create-consultation"],{queryParams: {id: id , lastName: doctorLastName}})
      }
    )
  }
}
