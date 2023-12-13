import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {DataService} from "../service/data.service";
import {Consultation} from "../models/Models";
import {formatDate} from "@angular/common";


@Component({
  selector: 'app-consultations',
  templateUrl: './consultations.component.html',
  styleUrls: ['./consultations.component.css']
})
export class ConsultationsComponent implements OnInit {
  deleteMessage: string = '';
  consultations: Consultation[] | any;

  constructor(
    public service: DataService,
  ) {
  }

  ngOnInit() {
    this.getAllConsultations();
  }

  createConsultation() {

  }

  private getAllConsultations() {
    this.service.getAllConsultations().subscribe(
      response => {
        console.log(response)
        this.consultations = response
      })
  }

  deleteConsultation(id: number) {

  }

  updateConsultation(id: number) {

  }

  protected readonly formatDate = formatDate;
  protected readonly Consultation = Consultation;
}
