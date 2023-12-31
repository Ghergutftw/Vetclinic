import {Component, OnInit} from '@angular/core';
import {DataService} from "../service/data.service";
import {Router} from "@angular/router";
import {Doctor} from "../models/Doctor";

@Component({
  selector: 'app-doctor-list',
  templateUrl: './doctor-list.component.html',
  styleUrls: ['./doctor-list.component.css']
})
export class DoctorListComponent implements OnInit{

  doctors : Doctor[] | any;

  deleteMessage : string = '';

  sortColumn: string = 'id';
  sortOrder: string = 'asc';

  constructor(
    public service:DataService,
    public router:Router

  ) {
  }
  ngOnInit(){
    this.refreshPage()
  }
  sort(column: string): void {
    if (this.sortColumn === column) {
      // If the same column is clicked again, reverse the sorting order
      this.sortOrder = this.sortOrder === 'asc' ? 'desc' : 'asc';
    } else {
      // If a new column is clicked, set the new column and reset the order to 'asc'
      this.sortColumn = column;
      this.sortOrder = 'asc';
    }

    // Perform the sorting
    this.doctors.sort((a : any, b:any) => {
      const aValue = this.getPropertyValue(a, column);
      const bValue = this.getPropertyValue(b, column);

      if (aValue < bValue) {
        return this.sortOrder === 'asc' ? -1 : 1;
      } else if (aValue > bValue) {
        return this.sortOrder === 'asc' ? 1 : -1;
      } else {
        return 0;
      }
    });
  }

  getPropertyValue(object: any, path: string): any {
    return path.split('.').reduce((o, i) => o[i], object);
  }
  refreshPage(){
    this.service.getAllDoctors().subscribe(
      response=>{
        console.log(response);
        this.doctors = response
      }
    )
  }

  deleteDoctor(id : number) {
    console.log(`Deleting doctor at ${id}`)
    this.service.deleteDoctorById(id).subscribe(() =>{
      console.log("Deleted successfully");
      this.deleteMessage = "DELETED SUCCESSFULLY"
      this.refreshPage();
      this.changeDeleteMessageInstantly()
    })
  }

  updateDoctor(id : number) {
    this.router.navigate(['update-doctor',id])
  }

  createDoctor() {
    this.router.navigate(['create-doctor'])
  }

  changeDeleteMessageInstantly(){
    setTimeout(() =>{
      this.deleteMessage = '';
    },2000)
  }
}
