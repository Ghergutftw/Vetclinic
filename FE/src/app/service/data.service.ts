import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Doctor} from "../doctor-list/doctor-list.component";
import {Animal} from "../animal-list/animal-list.component";
import {User} from "../users/users.component";
import {LoginModel} from "../models/LoginModel";
import {LoginResponse} from "../models/LoginResponse";
import {Consultation} from "../models/Models";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(
    public httpClient:HttpClient
  ) { }

  DOCTOR_API : string = "http://localhost:8081/doctor-service"
  ANIMAL_API : string = "http://localhost:8083/animal-service"
  USER_API : string = "http://localhost:8081/user-service"
  CONSULTATION_API : string = "http://localhost:8082/consultation-service"

  //DOCTORS API
  getAllDoctors(){
    return this.httpClient.get<Doctor[]>(`${this.DOCTOR_API}/get-all`)
  }

  deleteDoctorById(id : number){
    return this.httpClient.delete(`${this.DOCTOR_API}/delete/${id}`)
  }

  updateDoctorById(id:number , doctor:Doctor){
    return this.httpClient.put(`${this.DOCTOR_API}/update/${id}`,doctor)
  }

  createDoctor(doctor:Doctor){
    return this.httpClient.post<Doctor>(`${this.DOCTOR_API}/add`,doctor)
  }

  retrieveDoctorById(id:number){
    return this.httpClient.get<Doctor>(`${this.DOCTOR_API}/get/${id}`)
  }

  // DOCTORS API
  // ANIMALS API


  getAllAnimals(){
    return this.httpClient.get<Animal[]>(`${this.ANIMAL_API}/get-all`)
  }

  deleteAnimal(id:number){
    return this.httpClient.delete(`${this.ANIMAL_API}/delete/${id}`)
  }

  updateAnimal(id:number, animal:Animal){
    return this.httpClient.put(`${this.ANIMAL_API}/update/${id}` , animal)
  }

  retrieveAnimalById(id:number){
    return this.httpClient.get<Animal>(`${this.ANIMAL_API}/get/${id}`)
  }

  createAnimal(animal: Animal){
    return this.httpClient.post<Animal>(`${this.ANIMAL_API}/create` , animal)
  }

 // ANIMALS API
 //USERS API


  getAllUsers(){
    return this.httpClient.get<User[]>(`${this.USER_API}/get-all`)
  }

  login(user : LoginModel){
    return this.httpClient.post<LoginResponse>(`${this.USER_API}/login`,user)
  }

  logout(){
    return this.httpClient.get(`${this.USER_API}/logout`)
  }

  updateUserById(id: number , user:User){
    return this.httpClient.put(`http://localhost:8080/user/${id}`,user)
  }

  retrieveUserById(id:number){
    return this.httpClient.get<User>(`http://localhost:8080/user/${id}`)
  }

  getDecodedString(encodedPassword : string){
    return this.httpClient.get<string>(`http://localhost:8080/getDecoded/${encodedPassword}`);
  }
  getEncodedString(decodedPassword : string){
    return this.httpClient.get<string>(`http://localhost:8080/getEncoded/${decodedPassword}`);
  }


  //Consultations API

  getAllConsultations(){
    return this.httpClient.get<Consultation[]>(`${this.CONSULTATION_API}/get-all`)
  }

  createConsultation(consultation: Consultation) {
    return this.httpClient.post<Consultation>(`${this.CONSULTATION_API}/create`, consultation);
  }
}
