import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {DataService} from "../service/data.service";
import {Consultation} from "../models/Models";
import {formatDate} from "@angular/common";
import {DocsService} from "../service/docs.service";
import {saveAs} from "file-saver";


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
    this.docsService.exportToExcel().subscribe((blob: Blob) => {
      // Dynamically set the filename
      const fileName = 'Consultation_' + this.getCurrentDate() + '.xlsx';

      // Use the FileSaver library or other methods to trigger the download
      saveAs(blob, fileName);
    });
  }

  private getCurrentDate(): string {
    const currentDate = new Date();
    const year = currentDate.getFullYear();
    const month = ('0' + (currentDate.getMonth() + 1)).slice(-2);
    const day = ('0' + currentDate.getDate()).slice(-2);

    return `${year}-${month}-${day}`;
  }
}
