import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Doctor} from "../doctor-list/doctor-list.component";
import {Animal} from "../animal-list/animal-list.component";
import {User} from "../users/users.component";
import {LoginModel} from "../models/LoginModel";
import {LoginResponse} from "../models/LoginResponse";
import {Consultation} from "../models/Models";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(
    public httpClient:HttpClient
  ) { }

  BACKEND_API: string = environment.backendApi;
  DOCTOR_API: string = environment.doctorServiceName;
  ANIMAL_API: string = environment.animalServiceName;
  USER_API: string = environment.userServiceName;
  CONSULTATION_API: string = environment.consultationServiceName;

  //DOCTORS API
  getAllDoctors(){
    return this.httpClient.get<Doctor[]>(`${this.BACKEND_API}/${this.DOCTOR_API}/get-all`)
  }

  deleteDoctorById(id : number){
    return this.httpClient.delete(`${this.BACKEND_API}/${this.DOCTOR_API}/delete/${id}`)
  }

  updateDoctorById(id:number , doctor:Doctor){
    return this.httpClient.put(`${this.BACKEND_API}/${this.DOCTOR_API}/update/${id}`,doctor)
  }

  createDoctor(doctor:Doctor){
    return this.httpClient.post<Doctor>(`${this.BACKEND_API}/${this.DOCTOR_API}/add`,doctor)
  }

  retrieveDoctorById(id:number){
    return this.httpClient.get<Doctor>(`${this.BACKEND_API}/${this.DOCTOR_API}/get/${id}`)
  }

  // DOCTORS API
  // ANIMALS API


  getAllAnimals(){
    return this.httpClient.get<Animal[]>(`${this.BACKEND_API}/${this.ANIMAL_API}/get-all`)
  }

  deleteAnimal(id:number){
    return this.httpClient.delete(`${this.BACKEND_API}/${this.ANIMAL_API}/delete/${id}`)
  }

  updateAnimal(id:number, animal:Animal){
    return this.httpClient.put(`${this.BACKEND_API}/${this.ANIMAL_API}/update/${id}` , animal)
  }

  retrieveAnimalById(id:number){
    return this.httpClient.get<Animal>(`${this.BACKEND_API}/${this.ANIMAL_API}/get/${id}`)
  }

  createAnimal(animal: Animal){
    return this.httpClient.post<Animal>(`${this.BACKEND_API}/${this.ANIMAL_API}/create` , animal)
  }

 // ANIMALS API
 //USERS API


  getAllUsers(){
    return this.httpClient.get<User[]>(`${this.BACKEND_API}/${this.USER_API}/get-all`)
  }

  login(user : LoginModel){
    return this.httpClient.post<LoginResponse>(`${this.BACKEND_API}/${this.USER_API}/login`,user)
  }

  logout(){
    return this.httpClient.get(`${this.BACKEND_API}/${this.USER_API}/logout`)
  }

  updateUserById(id: number , user:User){
    return this.httpClient.put(`${this.BACKEND_API}/${this.USER_API}/${id}`,user)
  }

  retrieveUserById(id:number){
    return this.httpClient.get<User>(`${this.BACKEND_API}/${this.USER_API}/${id}`)
  }

  getDecodedString(encodedPassword : string){
    return this.httpClient.get<string>(`${this.BACKEND_API}/${this.USER_API}/${encodedPassword}`);
  }
  getEncodedString(decodedPassword : string){
    return this.httpClient.get<string>(`${this.BACKEND_API}/${this.USER_API}/${decodedPassword}`);
  }


  //Consultations API

  getAllConsultations(){
    return this.httpClient.get<Consultation[]>(`${this.BACKEND_API}/${this.CONSULTATION_API}/get-all`)
  }

  createConsultation(consultation: Consultation) {
    return this.httpClient.post<Consultation>(`${this.BACKEND_API}/${this.CONSULTATION_API}/create`, consultation);
  }

  getConsultationById(id: number) {
    return this.httpClient.get<Consultation>(`${this.BACKEND_API}/${this.CONSULTATION_API}/get/${id}`);
  }

  deleteConsultationById(id: number) {
    return this.httpClient.delete(`${this.BACKEND_API}/${this.CONSULTATION_API}/delete/${id}`);
  }

  updateConsultationById(id: number, consultation: Consultation) {
    return this.httpClient.put(`${this.BACKEND_API}/${this.CONSULTATION_API}/update/${id}`, consultation);
  }

}
