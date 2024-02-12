import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {DataService} from "../../service/data.service";
import {Animal} from "../../models/Animal";

@Component({
  selector: 'app-update',
  templateUrl: './update-animal.component.html',
  styleUrls: ['./update-animal.component.css']
})
export class UpdateAnimalComponent implements OnInit {

  animal!: Animal
  id!: number
  retrievedImage: any;
  image!: File;
  imagePreview: any;
  imageUploaded: boolean = false;
  toBeAdopted: boolean = false;

  constructor(
    private service: DataService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.animal = new Animal();
    this.refreshPage();
  }

  private refreshPage() {
    this.service.getAnimalById(this.id).subscribe(
      (response) => {
        this.animal = response;
      }
    )
    this.service.getImage(this.id).subscribe(
      value => {
        this.retrievedImage = value;
      }
    );
  }

  updateAnimal(id: number) {
    if (this.toBeAdopted) {
      this.service.abandonAnimalOwner(this.id).subscribe();
    }
    this.service.updateAnimal(id, this.animal).subscribe(() => {
      if (this.imageUploaded) {
        this.service.saveImage(this.id, this.image).subscribe(() => {
          this.router.navigate(["animals"]);
        });
      } else {
        this.router.navigate(["animals"]);
      }
    });
  }

  updateImage() {
    if (this.imageUploaded) {
      this.service.saveImage(this.id, this.image).subscribe(() => {
        this.refreshPage();
      });
    }
  }

  onFileSelected(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    if (inputElement.files && inputElement.files.length > 0) {
      this.image = inputElement.files[0];
      this.displayImagePreview();
    }
    this.imageUploaded = true;
  }

  displayImagePreview(): void {
    const reader = new FileReader();
    reader.onload = (e: any) => {
      this.imagePreview = e.target.result;
    };
    reader.readAsDataURL(this.image);
  }


  modify() {
    if (this.animal.forAdoption == true) {
      this.toBeAdopted = true;
    }
  }
}
