import {Component, OnInit} from '@angular/core';
import {Owner} from "../models/Owner";
import {DataService} from "../service/data.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-owner',
  templateUrl: './owner.component.html',
  styleUrls: ['./owner.component.css']
})
export class OwnerComponent implements OnInit{

  owners: Owner[] = []
  constructor(private dataService : DataService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.loadOwners();
  }
  loadOwners() {
    this.dataService.getAllOwners().subscribe(data => {
      this.owners = data;
    });
  }

  createOwner() {
    this.router.navigate(['/owners/create'])
  }

  deleteOwner(ownerId: number | undefined) {
    this.dataService.deleteOwner(ownerId).subscribe(response => {
      console.log('Delete response:', response);
      this.loadOwners();
    });
  }


  getOwnerById(id: number | undefined) {
    this.router.navigate(['owners', id, 'details']);
  }
}
