import { Component } from '@angular/core';
import {Animal} from "../animal-list.component";
import {DataService} from "../../service/data.service";
import {Router} from "@angular/router";

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

  createAnimal(animal: Animal) {
    console.log("Creating animal")
    console.log(animal.specie)
    this.createdAnimal.nickname = animal.nickname
    this.createdAnimal.animalType=animal.animalType
    this.createdAnimal.specie=animal.specie
    this.createdAnimal.age=animal.age
    this.createdAnimal.weight=animal.weight
    this.service.createAnimal(this.createdAnimal).subscribe(
      response =>{
        console.log(response)
        this.router.navigate(["/animals-list"])
      }
    )
  }
}
