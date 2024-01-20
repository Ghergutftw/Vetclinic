import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {LoginComponent} from "./login/login.component";
import {ErrorComponent} from "./error/error.component";
import {WelcomeComponent} from "./welcome/welcome.component";
import {LogoutComponent} from "./logout/logout.component";
import {DoctorsComponent} from "./doctors/doctors.component";
import {AnimalsComponent} from "./animals/animals.component";
import {ConsultationsComponent} from "./consultations/consultations.component";
import {RouterGuardService} from "./service/route-guard.service/router-guard.service";
import {CreateDoctorComponent} from "./doctors/create/create-doctor.component";
import {UsersComponent} from "./users/users.component";
import {UpdateDoctorComponent} from "./doctors/update/update-doctor.component";
import {CreateAnimalComponent} from "./animals/create/create-animal.component";
import {UpdateAnimalComponent} from "./animals/update/update-animal.component";
import {UpdateUserComponent} from "./users/update/update-user.component";
import {CreateConsultationComponent} from "./consultations/create/create-consultation.component";
import {AdoptionsComponent} from "./adoptions/adoptions.component";
import {AppointmentsComponent} from "./appointments/appointments.component";
import {UpdateConsultationComponent} from "./consultations/update/update-consultation.component";
import {CreateAppointmentComponent} from "./appointments/create/create-appointment.component";
import {UpdateAppointmentComponent} from "./appointments/update/update-appointment.component";

const routes: Routes = [
  {path: '', component:LoginComponent},
  {path: 'welcome',component:WelcomeComponent , canActivate:[RouterGuardService]},
  {path: 'appointments',component:AppointmentsComponent , canActivate:[RouterGuardService]},
  {path: 'create-appointment',component:CreateAppointmentComponent , canActivate:[RouterGuardService]},
  {path: 'update-appointment/:id',component:UpdateAppointmentComponent , canActivate:[RouterGuardService]},
  {path: 'animals',component:AnimalsComponent , canActivate:[RouterGuardService]},
  {path: 'create-animal',component:CreateAnimalComponent , canActivate:[RouterGuardService]},
  {path: 'update-animal/:id',component:UpdateAnimalComponent , canActivate:[RouterGuardService]},
  {path: 'doctors',component:DoctorsComponent , canActivate:[RouterGuardService]},
  {path: 'create-doctor',component:CreateDoctorComponent , canActivate:[RouterGuardService]},
  {path: 'update-doctor/:id',component:UpdateDoctorComponent , canActivate:[RouterGuardService]},
  {path: 'users',component:UsersComponent , canActivate:[RouterGuardService]},
  {path: 'update-user/:id',component:UpdateUserComponent , canActivate:[RouterGuardService]},
  {path: 'consultations',component:ConsultationsComponent , canActivate:[RouterGuardService]},
  {path: 'create-consultation',component:CreateConsultationComponent , canActivate:[RouterGuardService]},
  {path: 'update-consultation/:id',component:UpdateConsultationComponent , canActivate:[RouterGuardService]},
  {path: 'adoptions',component:AdoptionsComponent , canActivate:[RouterGuardService]},
  {path: 'login',component:LoginComponent},
  {path: 'logout',component:LogoutComponent , canActivate:[RouterGuardService]},
  {path: '**', component:ErrorComponent}
]
@NgModule({
  imports:[RouterModule.forRoot(routes)],
  exports:[RouterModule]
})
export class AppRoutingModule { }
