import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {DataService} from "../service/data.service";
import {Consultation} from "../models/Models";
import {formatDate} from "@angular/common";
import {DocsService} from "../service/docs.service";


@Component({
  selector: 'app-consultations',
  templateUrl: './consultations.component.html',
  styleUrls: ['./consultations.component.css']
})
export class ConsultationsComponent implements OnInit {
  deleteMessage: string = '';
  consultations: Consultation[] | any;

  constructor(
    public dataService: DataService,
    public docsService : DocsService
  ) {
  }

  ngOnInit() {
    this.getAllConsultations();
  }


  private getAllConsultations() {
    this.dataService.getAllConsultations().subscribe(
      response => {
        this.consultations = response
      })
  }

  deleteConsultation(id: number) {

  }

  updateConsultation(id: number) {

  }

  protected readonly formatDate = formatDate;
  protected readonly Consultation = Consultation;
  downloadConsultations() {
    this.docsService.exportToWord().subscribe((data: Blob) => {
      const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.wordprocessingml.document' });
      const url = window.URL.createObjectURL(blob);
      window.open(url);
    });

  }
}
