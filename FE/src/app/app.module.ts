import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { MenuComponent } from './menu/menu.component';
import { FooterComponent } from './footer/footer.component';
import { WelcomeComponent } from './welcome/welcome.component';
import {RouterOutlet} from "@angular/router";
import { AppRoutingModule } from './app-routing.module';
import { LoginComponent } from './login/login.component';
import { ErrorComponent } from './error/error.component';
import {FormsModule} from "@angular/forms";
import { DoctorListComponent } from './doctor-list/doctor-list.component';
import { AnimalListComponent } from './animal-list/animal-list.component';
import { LogoutComponent } from './logout/logout.component';
import { ConsultationsComponent } from './consultations/consultations.component';
import { PrescriptionsComponent } from './prescriptions/prescriptions.component';
import { WorkingHoursComponent } from './working-hours/working-hours.component';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import { CreateDoctorComponent } from './doctor-list/create-doctor/create-doctor.component';
import { UsersComponent } from './users/users.component';
import { UpdateDoctorComponent } from './doctor-list/update-doctor/update-doctor.component';
import { CreateAnimalComponent } from './animal-list/create-animal/create-animal.component';
import { UpdateAnimalComponent } from './animal-list/update-animal/update-animal.component';
import {AlertModule} from "./alert";
import { UpdateUserComponent } from './users/update-user/update-user.component';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatInputModule} from "@angular/material/input";
import { CreateConsultationComponent } from './consultations/create/create-consultation/create-consultation.component';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    FooterComponent,
    WelcomeComponent,
    LoginComponent,
    ErrorComponent,
    DoctorListComponent,
    AnimalListComponent,
    LogoutComponent,
    ConsultationsComponent,
    PrescriptionsComponent,
    WorkingHoursComponent,
    CreateDoctorComponent,
    UsersComponent,
    UpdateDoctorComponent,
    CreateAnimalComponent,
    UpdateAnimalComponent,
    UpdateUserComponent,
    CreateConsultationComponent
  ],
    imports: [
        BrowserModule,
        RouterOutlet,
        AlertModule,
        AppRoutingModule,
        FormsModule,
        HttpClientModule,
        FontAwesomeModule,
        BrowserAnimationsModule,
        MatInputModule
    ],
  providers: [HttpClient],
  bootstrap: [AppComponent]
})
export class AppModule { }
