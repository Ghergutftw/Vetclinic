import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpHeaders} from "@angular/common/http";
import {Login} from "../models/Login";
import {LoginResponse} from "../models/LoginResponse";
import {Consultation} from "../models/Consultation";
import {environment} from "../../environments/environment";
import {Doctor} from "../models/Doctor";
import {Animal} from "../models/Animal";
import {User} from "../models/User";
import {map, Observable} from "rxjs";
import {Appointment} from "../models/Appointment";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {Response} from "../models/Response";
import {Adoption} from "../models/Adoption";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(
    public httpClient:HttpClient,
    private sanitizer: DomSanitizer
  ) { }

  BACKEND_API: string = environment.backendApi;
  DOCTOR_API: string = environment.doctorServiceName;
  ANIMAL_API: string = environment.animalServiceName;
  USER_API: string = environment.userServiceName;
  CONSULTATION_API: string = environment.consultationServiceName;
  APPOINTMENT_API: string = environment.appointmentServiceName;
  ADOPTION_API: string = environment.adoptionServiceName;

  //DOCTORS API
  getAllDoctors(){
    return this.httpClient.get<Doctor[]>(`${this.BACKEND_API}/${this.DOCTOR_API}/get-all`)
  }

  deleteDoctorById(id: number | undefined){
    return this.httpClient.delete(`${this.BACKEND_API}/${this.DOCTOR_API}/delete/${id}`)
  }

  updateDoctorById(id:number , doctor:Doctor){
    return this.httpClient.put(`${this.BACKEND_API}/${this.DOCTOR_API}/update/${id}`,doctor)
  }

  getDoctorById(id: number | undefined){
    return this.httpClient.get<Doctor>(`${this.BACKEND_API}/${this.DOCTOR_API}/get/${id}`)
  }

  createDoctor(doctor:Doctor){
    return this.httpClient.post<Doctor>(`${this.BACKEND_API}/${this.DOCTOR_API}/add`,doctor)
  }

  retrieveDoctorById(id:number){
    return this.httpClient.get<Doctor>(`${this.BACKEND_API}/${this.DOCTOR_API}/get/${id}`)
  }

  getLastNames(){
    return this.httpClient.get<string[]>(`${this.BACKEND_API}/${this.DOCTOR_API}/last-names`)
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

  getImage(id: number | undefined): Observable<SafeUrl> {
    return this.httpClient.get(`${this.BACKEND_API}/${this.ANIMAL_API}/get-image/${id}`, { responseType: 'arraybuffer' })
      .pipe(map(res => {
        const blob = new Blob([res], { type: 'image/png' }); // Adjust the type based on the actual image type
        return this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(blob));
      }));
  }

  saveImage(id: number | undefined, image: File) {
    const formData = new FormData();
    formData.append('image', image, image.name);
    const headers = new HttpHeaders();
    headers.append('Content-Type', 'multipart/form-data');
    return this.httpClient.post(`${this.BACKEND_API}/${this.ANIMAL_API}/post-image/${id}`, formData , {headers: headers});
  }
  // TODO : ABANDONING ANIMAL
  abandonAnimal(id: number | undefined) {
    return this.httpClient.post(`${this.BACKEND_API}/${this.APPOINTMENT_API}/owner/abandon/${id}`, null);
  }
 // ANIMALS API
 //USERS API


  getAllUsers(){
    return this.httpClient.get<User[]>(`${this.BACKEND_API}/${this.USER_API}/get-all`)
  }

  login(user : Login){
    return this.httpClient.post<LoginResponse>(`${this.BACKEND_API}/${this.USER_API}/login`,user)
  }

  createUser(user:User){
    return this.httpClient.post<Response>(`${this.BACKEND_API}/${this.USER_API}/create`,user)
  }

  forgotPassword(email:string){
    return this.httpClient.get(`${this.BACKEND_API}/${this.USER_API}/forgot-password/${email}`)
  }

  logout(){
    return this.httpClient.get(`${this.BACKEND_API}/${this.USER_API}/logout`)
  }

  updateUserById(id: number , user:User){
    return this.httpClient.put(`${this.BACKEND_API}/${this.USER_API}/${id}`,user)
  }

  getUserById(id:number){
    return this.httpClient.get<User>(`${this.BACKEND_API}/${this.USER_API}/get/${id}`)
  }

  getDecodedString(encodedPassword?: string ){
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

  //Appointments API

  getAllAppointments(){
    return this.httpClient.get<Appointment[]>(`${this.BACKEND_API}/${this.APPOINTMENT_API}/get-all`)
  }

  createAppointment(appointment: Appointment) {
    return this.httpClient.post<Appointment>(`${this.BACKEND_API}/${this.APPOINTMENT_API}/create`, appointment);
  }

  getAppointmentById(id: number) {
    return this.httpClient.get<Appointment>(`${this.BACKEND_API}/${this.APPOINTMENT_API}/get/${id}`);
  }

  deleteAppointment(id: number | undefined) {
    return this.httpClient.delete(`${this.BACKEND_API}/${this.APPOINTMENT_API}/delete/${id}`);
  }

  updateAppointment(id: number, appointment: Appointment) {
    return this.httpClient.put(`${this.BACKEND_API}/${this.APPOINTMENT_API}/update/${id}`, appointment);
  }

  // Change status of appointment
  updateStatus(id: number | undefined, status: string) {
    return this.httpClient.post(`${this.BACKEND_API}/${this.APPOINTMENT_API}/${id}/update-status/${status}`, null);
  }

  getAnimalDiseases(): Observable<string[]> {
    return this.httpClient.get<string[]>(`${this.BACKEND_API}/${this.CONSULTATION_API}/diagnoses`);
  }

  getRecommendations(): Observable<string[]> {
    return this.httpClient.get<string[]>(`${this.BACKEND_API}/${this.CONSULTATION_API}/recommendations`);
  }

  getTreatments(): Observable<string[]> {
    return this.httpClient.get<string[]>(`${this.BACKEND_API}/${this.CONSULTATION_API}/treatments`);
  }

//   //Adoptions API
//
//   getAllAdoptions(){
//     return this.httpClient.get<Animal[]>(`${this.BACKEND_API}/${this.ADOPTION_API}/get-all`)
//   }

  createAdoption(adoption: Adoption) {
    return this.httpClient.post<Animal>(`${this.BACKEND_API}/${this.ADOPTION_API}/create`, adoption);
  }



}
