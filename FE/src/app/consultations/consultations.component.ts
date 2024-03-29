import {Component, OnInit} from '@angular/core';
import {DataService} from "../service/data.service";
import {Consultation} from "../models/Consultation";
import {formatDate} from "@angular/common";
import {DocsService} from "../service/docs.service";
import {saveAs} from "file-saver";
import {Router} from "@angular/router";


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
    public docsService : DocsService,
    public router : Router
  ) {
  }

  ngOnInit() {
    this.getAllConsultations();
  }


  private getAllConsultations() {
    this.consultations = [];
    this.dataService.getAllConsultations().subscribe(
      response => {
        this.consultations = response
      })
  }

  deleteConsultation(id: number) {
    this.dataService.deleteConsultationById(id).subscribe(
      () => {
        this.deleteMessage = 'Consultation deleted successfully!';
        this.getAllConsultations();
      }
    )
  }

  updateConsultation(id: number) {
    this.router.navigate(['update-consultation', id]);
  }

  protected readonly formatDate = formatDate;
  protected readonly Consultation = Consultation;

  downloadConsultationsExcel() {
    this.docsService.exportToExcel().subscribe((blob: Blob) => {
      const fileName = 'Consultation_' + this.getCurrentDateTime() + '.xlsx';
      saveAs(blob, fileName);
    });
  }

  downloadConsultationsWord() {
    this.docsService.exportToWord().subscribe((data: ArrayBuffer) => {
      const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.wordprocessingml.document' });

      // Dynamically set the filename
      const fileName = 'Consultation_' + this.getCurrentDateTime() + '.docx';

      // Use the FileSaver library or other methods to trigger the download
      saveAs(blob, fileName);
    });
  }


  private getCurrentDateTime(): string {
    const currentDate = new Date();
    const year = currentDate.getFullYear();
    const month = ('0' + (currentDate.getMonth() + 1)).slice(-2);
    const day = ('0' + currentDate.getDate()).slice(-2);
    const hours = ('0' + currentDate.getHours()).slice(-2);
    const minutes = ('0' + currentDate.getMinutes()).slice(-2);
    const seconds = ('0' + currentDate.getSeconds()).slice(-2);

    return `${year}-${month}-${day}_${hours}:${minutes}:${seconds}`;
  }
}
