import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {LoginComponent} from "./login/login.component";
import {ErrorComponent} from "./error/error.component";
import {WelcomeComponent} from "./welcome/welcome.component";
import {LogoutComponent} from "./logout/logout.component";
import {DoctorListComponent} from "./doctor-list/doctor-list.component";
import {AnimalListComponent} from "./animal-list/animal-list.component";
import {ConsultationsComponent} from "./consultations/consultations.component";
import {PrescriptionsComponent} from "./prescriptions/prescriptions.component";
import {WorkingHoursComponent} from "./working-hours/working-hours.component";
import {RouterGuardService} from "./service/route-guard.service/router-guard.service";
import {CreateDoctorComponent} from "./doctor-list/create-doctor/create-doctor.component";
import {UsersComponent} from "./users/users.component";
import {UpdateDoctorComponent} from "./doctor-list/update-doctor/update-doctor.component";
import {CreateAnimalComponent} from "./animal-list/create-animal/create-animal.component";
import {UpdateAnimalComponent} from "./animal-list/update-animal/update-animal.component";
import {UpdateUserComponent} from "./users/update-user/update-user.component";
import {CreateConsultationComponent} from "./consultations/create/create-consultation.component";
import {AdoptionsComponent} from "./adoptions/adoptions.component";

const routes: Routes = [
  {path: '', component:LoginComponent},
  {path: 'welcome',component:WelcomeComponent , canActivate:[RouterGuardService]},
  {path: 'animals-list',component:AnimalListComponent , canActivate:[RouterGuardService]},
  {path: 'doctors-list',component:DoctorListComponent , canActivate:[RouterGuardService]},
  {path: 'create-doctor',component:CreateDoctorComponent , canActivate:[RouterGuardService]},
  {path: 'update-doctor/:id',component:UpdateDoctorComponent , canActivate:[RouterGuardService]},
  {path: 'create-animal',component:CreateAnimalComponent , canActivate:[RouterGuardService]},
  {path: 'update-animal/:id',component:UpdateAnimalComponent , canActivate:[RouterGuardService]},
  {path: 'users-list',component:UsersComponent , canActivate:[RouterGuardService]},
  {path: 'update-user/:id',component:UpdateUserComponent , canActivate:[RouterGuardService]},
  {path: 'consultations',component:ConsultationsComponent , canActivate:[RouterGuardService]},
  {path: 'create-consultation',component:CreateConsultationComponent , canActivate:[RouterGuardService]},
  {path: 'prescriptions',component:PrescriptionsComponent , canActivate:[RouterGuardService]},
  {path: 'adoptions',component:AdoptionsComponent , canActivate:[RouterGuardService]},
  {path: 'workingHours',component:WorkingHoursComponent , canActivate:[RouterGuardService]},
  {path: 'login',component:LoginComponent},
  {path: 'logout',component:LogoutComponent , canActivate:[RouterGuardService]},
  {path: '**', component:ErrorComponent}
]
@NgModule({
  imports:[RouterModule.forRoot(routes)],
  exports:[RouterModule]
})
export class AppRoutingModule { }
