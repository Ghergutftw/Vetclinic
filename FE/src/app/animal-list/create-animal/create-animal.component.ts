import { Component } from '@angular/core';
import {DataService} from "../../service/data.service";
import {Router} from "@angular/router";
import {Animal} from "../../models/Animal";

@Component({
  selector: 'app-create-animal',
  templateUrl: './create-animal.component.html',
  styleUrls: ['./create-animal.component.css']
})
export class CreateAnimalComponent {

  createdAnimal !: Animal
  speciesOptions: string[] = [];

  constructor(
    private service : DataService,
    private router: Router
  ) {
  }

  ngOnInit(){
    this.createdAnimal = new Animal(0, "", "", "", 0, 0)
  }

  updateSpecies() {
    // Add logic to fetch top 5 species based on the selected animalType
    // For simplicity, I'll provide a sample data for each animalType
    switch (this.createdAnimal.animalType) {
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

  createAnimal() {
    this.service.createAnimal(this.createdAnimal).subscribe(
      () =>{
        this.router.navigate(["/animals-list"])
      }
    )
  }
}
