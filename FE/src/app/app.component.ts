import { Component } from '@angular/core';
import {Alert, AlertService} from "./alert";
import {faGithub} from "@fortawesome/free-brands-svg-icons";


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

  faGitHub = faGithub

  constructor(protected alertService:AlertService) {
  }

  sites: string[] =[
    'Github'
  ]


}
