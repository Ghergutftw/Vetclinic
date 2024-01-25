import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {DataService} from "../../service/data.service";
import {Animal} from "../../models/Animal";
import {HttpClient, HttpEvent} from "@angular/common/http";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-update',
  templateUrl: './update-animal.component.html',
  styleUrls: ['./update-animal.component.css']
})
export class UpdateAnimalComponent implements OnInit{

  animal!: Animal
  id!: number
  retrievedImage: any;
  image!: File;
  imagePreview: any;

  constructor(
    private service: DataService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.animal = new Animal(0, "", "", "", 0, 0)
    this.refreshPage();

  }

  private refreshPage() {
    this.service.retrieveAnimalById(this.id).subscribe(
      response => {
        this.animal = new Animal(response.id, response.nickname, response.animalType, response.specie, response.age, response.weight);
      }
    )

    this.service.getImage(this.id).subscribe(
      value => {
        this.retrievedImage = value;
      }
    );
  }

  updateAnimal(id: number) {
    this.service.updateAnimal(id, this.animal).subscribe(
      () => {
        this.service.saveImage(this.id, this.image).subscribe(
          () => {
            this.router.navigate(["animals"])
          }
        )
      }
    )
  }

  updateImage() {
    this.service.saveImage(this.id, this.image)
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

}
