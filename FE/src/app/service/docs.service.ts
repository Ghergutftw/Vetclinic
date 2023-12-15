import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DocsService {

  DOCTOR_API : string = "http://localhost:8081/doctor-service"
  ANIMAL_API : string = "http://localhost:8080/animal-service"
  USER_API : string = "http://localhost:8081/user-service"
  CONSULTATION_API : string = "http://localhost:8082/consultation-service"

  constructor(
    public http:HttpClient
  ) { }

  exportToWord(): Observable<Blob> {
    return this.http.get(`${this.CONSULTATION_API}/export`, { responseType: 'blob' });
  }
}


