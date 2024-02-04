import {Component} from '@angular/core';
import {DataService} from "../../service/data.service";
import {Router} from "@angular/router";
import {Animal} from "../../models/Animal";

@Component({
  selector: 'app-create',
  templateUrl: './create-animal.component.html',
  styleUrls: ['./create-animal.component.css']
})
export class CreateAnimalComponent {

  createdAnimal !: Animal
  speciesOptions: string[] = [];
  retrievedImage: any;
  image!: File;
  imagePreview: any;

  constructor(
    private service : DataService,
    private router: Router
  ) {
  }

  ngOnInit(){
    this.createdAnimal = new Animal(0, "", "", "", 0, 0)
  }

  updateSpecies() {
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
      (animal) =>{
        this.service.saveImage(animal.id,this.image).subscribe(
          () =>{
            this.router.navigate(["/animals"])
          }
        )
      }
    )
  }
  onFileSelected(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    if (inputElement.files && inputElement.files.length > 0) {
      this.image = inputElement.files[0];
      this.displayImagePreview();
    }
  }

  displayImagePreview(): void {
    const reader = new FileReader();
    reader.onload = (e: any) => {
      this.imagePreview = e.target.result;
    };
    reader.readAsDataURL(this.image);
  }

  isFormValid(): "" | undefined | 0 | File{
    return (
      this.createdAnimal.nickname &&
      this.createdAnimal.animalType &&
      this.createdAnimal.specie &&
      this.createdAnimal.age &&
      this.createdAnimal.weight &&
      this.image
    );
  }
}
