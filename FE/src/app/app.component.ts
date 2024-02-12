import { Component } from '@angular/core';
import {AlertService} from "./alert";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  options={
    autoClose: false,
    keepAfterRouteChange:false
  };
  title = 'FE-VetClinic';


  constructor(protected alertService:AlertService) {
  }

  sites: string[] =[
    'Github'
  ]


}
