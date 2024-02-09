import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
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
import {Owner} from "../models/Owner";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(
    public httpClient: HttpClient,
    private sanitizer: DomSanitizer
  ) {
  }

  BACKEND_API: string = environment.backendApi;
  DOCTOR_API: string = environment.doctorServiceName;
  ANIMAL_API: string = environment.animalServiceName;
  USER_API: string = environment.userServiceName;
  CONSULTATION_API: string = environment.consultationServiceName;
  APPOINTMENT_API: string = environment.appointmentServiceName;
  ADOPTION_API: string = environment.adoptionServiceName;
  OWNER_API: string = environment.ownerServiceName;

  //DOCTORS API
  getAllDoctors() {
    return this.httpClient.get<Doctor[]>(`${this.BACKEND_API}/${this.DOCTOR_API}`)
  }

  deleteDoctorById(id: number | undefined) {
    return this.httpClient.delete(`${this.BACKEND_API}/${this.DOCTOR_API}/${id}`)
  }

  updateDoctorById(id: number, doctor: Doctor) {
    return this.httpClient.put(`${this.BACKEND_API}/${this.DOCTOR_API}/${id}`, doctor)
  }

  getDoctorById(id: number | undefined) {
    return this.httpClient.get<Doctor>(`${this.BACKEND_API}/${this.DOCTOR_API}/${id}`)
  }

  createDoctor(doctor: Doctor) {
    return this.httpClient.post<Doctor>(`${this.BACKEND_API}/${this.DOCTOR_API}`, doctor)
  }

  retrieveDoctorById(id: number) {
    return this.httpClient.get<Doctor>(`${this.BACKEND_API}/${this.DOCTOR_API}/${id}`)
  }

  getLastNames() {
    return this.httpClient.get<string[]>(`${this.BACKEND_API}/${this.DOCTOR_API}/last-names`)
  }

  // DOCTORS API
  // ANIMALS API

  getAllAnimals() {
    return this.httpClient.get<Animal[]>(`${this.BACKEND_API}/${this.ANIMAL_API}`)
  }

  abandonAnimal(id: number | undefined) {

    const params = new HttpParams().set('id', id?.toString() || '');

    return this.httpClient.post(`${this.BACKEND_API}/${this.ANIMAL_API}/abandon`, null, {params: params});
  }

  deleteAnimal(id: number) {
    return this.httpClient.delete(`${this.BACKEND_API}/${this.ANIMAL_API}/${id}`)
  }

  updateAnimal(id: number, animal: Animal) {
    return this.httpClient.put(`${this.BACKEND_API}/${this.ANIMAL_API}/${id}`, animal)
  }

  retrieveAnimalById(id: number) {
    return this.httpClient.get<Animal>(`${this.BACKEND_API}/${this.ANIMAL_API}/${id}`)
  }

  createAnimal(animal: Animal) {
    return this.httpClient.post<Animal>(`${this.BACKEND_API}/${this.ANIMAL_API}`, animal)
  }

  getImage(id: number | undefined): Observable<SafeUrl> {
    const params = new HttpParams().set('id', id?.toString() || '');

    return this.httpClient.get(`${this.BACKEND_API}/${this.ANIMAL_API}/images`, {
      responseType: 'arraybuffer',
      params: params
    })
      .pipe(map(res => {
        const blob = new Blob([res], {type: 'image/png'});
        return this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(blob));
      }));
  }

  saveImage(id: number | undefined, image: File) {
    const formData = new FormData();
    formData.append('image', image, image.name);
    const headers = new HttpHeaders();
    headers.append('Content-Type', 'multipart/form-data');
    return this.httpClient.post(`${this.BACKEND_API}/${this.ANIMAL_API}/post-image/${id}`, formData, {headers: headers});
  }

  abandonAnimalOwner(id: number | undefined) {
    return this.httpClient.post(`${this.BACKEND_API}/${this.APPOINTMENT_API}/owner/abandon/${id}`, null);
  }

  // ANIMALS API
  //USERS API


  getAllUsers() {
    return this.httpClient.get<User[]>(`${this.BACKEND_API}/${this.USER_API}`)
  }

  login(user: Login) {
    return this.httpClient.post<LoginResponse>(`${this.BACKEND_API}/${this.USER_API}/login`, user)
  }

  createUser(user: User) {
    return this.httpClient.post<Response>(`${this.BACKEND_API}/${this.USER_API}`, user)
  }

  // TODO : Forgot password BE + FE
  forgotPassword(email: string) {
    return this.httpClient.get(`${this.BACKEND_API}/${this.USER_API}/forgot-password/${email}`)
  }

  logout() {
    return this.httpClient.get(`${this.BACKEND_API}/${this.USER_API}/logout`)
  }

  updateUserById(id: number, user: User) {
    return this.httpClient.put(`${this.BACKEND_API}/${this.USER_API}/${id}`, user)
  }

  getUserById(id: number) {
    return this.httpClient.get<User>(`${this.BACKEND_API}/${this.USER_API}/${id}`)
  }

  getDecodedString(encodedPassword?: string) {
    return this.httpClient.get<string>(`${this.BACKEND_API}/${this.USER_API}/${encodedPassword}`);
  }

  getEncodedString(decodedPassword: string) {
    return this.httpClient.get<string>(`${this.BACKEND_API}/${this.USER_API}/${decodedPassword}`);
  }


  //Consultations API

  getAllConsultations() {
    return this.httpClient.get<Consultation[]>(`${this.BACKEND_API}/${this.CONSULTATION_API}`)
  }

  createConsultation(consultation: Consultation) {
    return this.httpClient.post<Consultation>(`${this.BACKEND_API}/${this.CONSULTATION_API}`, consultation);
  }

  getConsultationById(id: number) {
    return this.httpClient.get<Consultation>(`${this.BACKEND_API}/${this.CONSULTATION_API}/${id}`);
  }

  deleteConsultationById(id: number) {
    return this.httpClient.delete(`${this.BACKEND_API}/${this.CONSULTATION_API}/${id}`);
  }

  updateConsultationById(id: number, consultation: Consultation) {
    return this.httpClient.put(`${this.BACKEND_API}/${this.CONSULTATION_API}/${id}`, consultation);
  }

  //Appointments API

  getAllAppointments() {
    return this.httpClient.get<Appointment[]>(`${this.BACKEND_API}/${this.APPOINTMENT_API}`)
  }

  createAppointment(appointment: Appointment) {
    return this.httpClient.post<Appointment>(`${this.BACKEND_API}/${this.APPOINTMENT_API}`, appointment);
  }

  getAppointmentById(id: number) {
    return this.httpClient.get<Appointment>(`${this.BACKEND_API}/${this.APPOINTMENT_API}/${id}`);
  }

  deleteAppointment(id: number | undefined) {
    return this.httpClient.delete(`${this.BACKEND_API}/${this.APPOINTMENT_API}/${id}`);
  }

  updateAppointment(id: number, appointment: Appointment) {
    return this.httpClient.put(`${this.BACKEND_API}/${this.APPOINTMENT_API}/${id}`, appointment);
  }

  // Change status of appointment
  updateStatus(id: number | undefined, status: string) {
    const params = new HttpParams().set('status', status?.toString() || '');

    return this.httpClient.post(`${this.BACKEND_API}/${this.APPOINTMENT_API}/${id}/update-status`, null, {params: params});
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

//   TODO : Make an adoption page that switches or something ¯\_(ツ)_/¯
//   //Adoptions API
//
//   getAllAdoptions(){
//     return this.httpClient.get<Animal[]>(`${this.BACKEND_API}/${this.ADOPTION_API}/get-all`)
//   }

  createAdoption(adoption: Adoption) {
    return this.httpClient.post<Animal>(`${this.BACKEND_API}/${this.ADOPTION_API}`, adoption);
  }

//
  getAllOwners(): Observable<Owner[]> {
    return this.httpClient.get<Owner[]>(`${this.BACKEND_API}/${this.APPOINTMENT_API}/${this.OWNER_API}`);
  }

  createOwner(owner: Owner): Observable<Owner> {
    return this.httpClient.post<Owner>(`${this.BACKEND_API}/${this.APPOINTMENT_API}/${this.OWNER_API}`, owner);
  }

  deleteOwner(id: number | undefined): Observable<Response> {
    return this.httpClient.delete<Response>(`${this.BACKEND_API}/${this.APPOINTMENT_API}/${this.OWNER_API}/${id}`);
  }

  updateOwner(id: number, owner: Owner): Observable<Owner> {
    return this.httpClient.put<Owner>(`${this.BACKEND_API}/${this.APPOINTMENT_API}/${this.OWNER_API}/${id}`, owner);
  }

  getOwnerById(id: number): Observable<Owner> {
    return this.httpClient.get<Owner>(`${this.BACKEND_API}/${this.APPOINTMENT_API}/${this.OWNER_API}/${id}`);
  }

}
