import {Component, OnInit} from '@angular/core';
import {Consultation} from "../../models/Consultation";
import {DataService} from "../../service/data.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Doctor} from "../../models/Doctor";
import {Animal} from "../../models/Animal";

@Component({
  selector: 'app-update',
  templateUrl: './update-consultation.component.html',
  styleUrls: ['./update-consultation.component.css']
})
export class UpdateConsultationComponent implements OnInit {

  consultation: Consultation = new Consultation();
  id: number = 0;
  speciesOptions: string[] = [];
  doctors: Doctor[] = [];
  lastNames: string[] = [];
  consultatedAnimal: Animal = new Animal();
  constructor(public dataService: DataService,
              public router: Router,
              public activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.params['id'];

    this.dataService.getConsultationById(this.id).subscribe(
      response => {
        this.consultation = response;
      }
    )

    this.dataService.retrieveAnimalById(this.id).subscribe(
      response => {
        this.consultatedAnimal = response;
        this.updateSpecies();
      }
    )

    this.dataService.getAllDoctors().subscribe(
      response => {
        this.doctors = response;
      }
    )

    this.dataService.getLastNames().subscribe(
      response => {
        this.lastNames = response;
      }
    )


  }

  updateSpecies() {
    switch (this.consultatedAnimal.animalType) {
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

  updateConsultation() {
    this.dataService.updateConsultationById(this.id, this.consultation).subscribe(
      value => {
        this.router.navigate(['/consultations'])
      }
    )
  }


}
