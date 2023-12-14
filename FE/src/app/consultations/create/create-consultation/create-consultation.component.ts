import {Component, NgIterable, OnInit} from '@angular/core';
import {Doctor} from "../../../doctor-list/doctor-list.component";
import {DataService} from "../../../service/data.service";
import {Animal} from "../../../animal-list/animal-list.component";
import {Consultation} from "../../../models/Models";
import {formatDate} from "@angular/common";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-consultation',
  templateUrl: './create-consultation.component.html',
  styleUrls: ['./create-consultation.component.css']
})
export class CreateConsultationComponent implements OnInit{
  defaultDate = new Date();
  doctors: Doctor[] = [];
  speciesOptions: string[] = [];
  consultation: Consultation = new Consultation(0,
    new Date(),
    "",
    "",
    "",
    "",
    "",
    0,
    new Animal(0, "", "", "",0,0));
  response : string = "";
  constructor(
    public service: DataService,
    public router: Router
  ) {
  }

  updateSpecies() {

    switch (this.consultation.consultatedAnimal.animalType) {
      case 'Cat':
        this.speciesOptions = ['Siamese', 'Persian', 'Maine Coon', 'Ragdoll', 'Bengal'];
        break;
      case 'Dog':
        this.speciesOptions = ['Labrador Retriever', 'German Shepherd', 'Golden Retriever', 'Bulldog', 'Poodle'];
        break;
      case 'Hamster':
        this.speciesOptions = ['Syrian', 'Dwarf Campbells Russian', 'Roborovski', 'Chinese', 'Winter White'];
        break;
      case 'Rabbit':
        this.speciesOptions = ['Holland Lop', 'Mini Rex', 'Netherland Dwarf', 'Lionhead', 'Flemish Giant'];
        break;
      default:
        this.speciesOptions = [];
        break;
    }
  }

  ngOnInit(): void {
    this.service.getAllDoctors().subscribe(
      response => {
        this.doctors = response;
      }
    )
  }

  createConsultation() {
    this.service.createConsultation(this.consultation).subscribe(
      value => {
        console.log(value)
      }
    );
    this.router.navigate(["/consultations"])

  }
}
