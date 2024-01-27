import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {MenuComponent} from './menu/menu.component';
import {FooterComponent} from './footer/footer.component';
import {WelcomeComponent} from './welcome/welcome.component';
import {RouterOutlet} from "@angular/router";
import {AppRoutingModule} from './app-routing.module';
import {LoginComponent} from './login/login.component';
import {ErrorComponent} from './error/error.component';
import {FormsModule} from "@angular/forms";
import {DoctorsComponent} from './doctors/doctors.component';
import {AnimalsComponent} from './animals/animals.component';
import {LogoutComponent} from './logout/logout.component';
import {ConsultationsComponent} from './consultations/consultations.component';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import {CreateDoctorComponent} from './doctors/create/create-doctor.component';
import {UsersComponent} from './users/users.component';
import {UpdateDoctorComponent} from './doctors/update/update-doctor.component';
import {CreateAnimalComponent} from './animals/create/create-animal.component';
import {UpdateAnimalComponent} from './animals/update/update-animal.component';
import {AlertModule} from "./alert";
import {UpdateUserComponent} from './users/update/update-user.component';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatInputModule} from "@angular/material/input";
import {CreateConsultationComponent} from './consultations/create/create-consultation.component';
import {CorsInterceptor} from "./interceptors/cors-interceptor.interceptor";
import {AdoptionsComponent} from './adoptions/adoptions.component';
import {AppointmentsComponent} from './appointments/appointments.component';
import {CreateAppointmentComponent} from './appointments/create/create-appointment.component';
import {UpdateConsultationComponent} from './consultations/update/update-consultation.component';
import {UpdateAppointmentComponent} from './appointments/update/update-appointment.component';
import {SignupComponent} from './login/signup/signup.component';
import {NotifierModule} from "angular-notifier";
import { StarSortPipe } from './adoptions/star-sort.pipe';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    FooterComponent,
    WelcomeComponent,
    LoginComponent,
    ErrorComponent,
    DoctorsComponent,
    AnimalsComponent,
    LogoutComponent,
    ConsultationsComponent,
    CreateDoctorComponent,
    UsersComponent,
    UpdateDoctorComponent,
    CreateAnimalComponent,
    UpdateAnimalComponent,
    UpdateUserComponent,
    CreateConsultationComponent,
    AdoptionsComponent,
    AppointmentsComponent,
    CreateAppointmentComponent,
    UpdateConsultationComponent,
    UpdateAppointmentComponent,
    SignupComponent,
    StarSortPipe
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
    MatInputModule,
    NotifierModule
  ],
  providers: [HttpClient,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: CorsInterceptor,
      multi: true,
    }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
