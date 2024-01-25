import {Component, OnInit} from '@angular/core';
import {DataService} from "../service/data.service";
import {Router} from "@angular/router";
import {Doctor} from "../models/Doctor";
import {User} from "../models/User";

@Component({
  selector: 'app-doctors',
  templateUrl: './doctors.component.html',
  styleUrls: ['./doctors.component.css']
})
export class DoctorsComponent implements OnInit {

  doctors: Doctor[] ;

  deleteMessage: string = '';

  sortColumn: string = 'id';
  sortOrder: string = 'asc';
  users: User[] = [];

  constructor(
    public service: DataService,
    public router: Router
  ) {
    this.doctors = [];
  }

  ngOnInit() {
    this.refreshPage()
  }

  refreshPage() {
    this.service.getAllDoctors().subscribe(
      response => {
        this.doctors = response;
        this.service.getAllUsers().subscribe(
          userResponse => {
            this.doctors.forEach(doctor => {
              const foundUser = userResponse.find((user: User) => user.id === doctor.id);
              if (foundUser) {
                doctor.user = foundUser;
              }
            });
          }
        );
      }
    );

  }

  deleteDoctor(id: number | undefined) {
    this.service.deleteDoctorById(id).subscribe(() => {
      this.deleteMessage = "DELETED SUCCESSFULLY"
      this.refreshPage();
      this.changeDeleteMessageInstantly()
    })
  }

  updateDoctor(id: number | undefined) {
    this.router.navigate(['update-doctor', id])
  }

  createDoctor() {
    this.router.navigate(['create-doctor'])
  }

  changeDeleteMessageInstantly() {
    setTimeout(() => {
      this.deleteMessage = '';
    }, 2000)
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
    this.doctors.sort((a: any, b: any) => {
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

}
