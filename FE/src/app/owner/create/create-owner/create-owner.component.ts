import {Component} from '@angular/core';
import {DataService} from "../../../service/data.service";
import {Router} from "@angular/router";
import {Owner} from "../../../models/Owner";

@Component({
  selector: 'app-create-owner',
  templateUrl: './create-owner.component.html',
  styleUrls: ['./create-owner.component.css']
})
export class CreateOwnerComponent{

  owner : Owner = new Owner();

  constructor(public service: DataService,
              public router: Router) {
  }

  createOwner() {
    this.service.createOwner(this.owner).subscribe(()=>
    {
      this.router.navigate(['/owners'])
    })
  }
}
