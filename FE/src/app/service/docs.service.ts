import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Consultation} from "../models/Consultation";

@Injectable({
  providedIn: 'root'
})
export class DocsService {

  DOCTOR_API: string = "http://localhost:8081/doctor-service"
  ANIMAL_API: string = "http://localhost:8080/animal-service"
  USER_API: string = "http://localhost:8081/user-service"

  CONSULTATION_API : string = "consultation-service"
  BACKEND_API : string = "http://localhost:8765"

  constructor(
    public http: HttpClient
  ) {
  }

  exportToExcel(): Observable<Blob> {
    return this.http.get(`${this.BACKEND_API}/${this.CONSULTATION_API}/download-excel`, {responseType: 'arraybuffer'})
      .pipe(
        map((data: ArrayBuffer) => {
          const blob = new Blob([data], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});
          return blob;
        })
      );
  }
  exportToWord(): Observable<ArrayBuffer> {
    return this.http.get(`${this.BACKEND_API}/${this.CONSULTATION_API}/download-word`, { responseType: 'arraybuffer' });
  }

  getReceipt(email: string | undefined , consultation : Consultation){
    const params = new HttpParams();
    return this.http.post(`${this.BACKEND_API}/${this.CONSULTATION_API}/get-receipt?email=${email}`,consultation);
  }

}


