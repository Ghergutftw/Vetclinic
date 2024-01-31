import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Owner} from "../../models/Owner";
import {DataService} from "../../service/data.service";
import { Animal } from 'src/app/models/Animal';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrl: './details.component.css'
})
export class DetailsComponent implements OnInit {

  ownerId: number = 0;
  owner: Owner = new Owner();
  animals: Animal[] = [];
  ownerAnimals: number[] | undefined = [];

  constructor(
    private route: ActivatedRoute,
    private service: DataService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.ownerId = +params['id'];
    });
    this.getAnimalsForOwner();
  }

  private getAnimalsForOwner() {
    this.service.getOwnerById(this.ownerId).subscribe(data => {
      this.owner = data;
      this.ownerAnimals = data.ownedAnimals;
      this.loadAnimalDetails();
    });
  }

  private loadAnimalDetails() {
    this.animals = [];
    if (this.ownerAnimals) {
      this.ownerAnimals.forEach((animalId) => {
        this.service.retrieveAnimalById(animalId).subscribe((data: Animal) => {
          data.image = this.service.getImage(data.id);
          this.animals.push(data);
        });
      });
    }
  }

  updateOwner() {
    this.service.updateOwner(this.ownerId, this.owner).subscribe(data => {
      this.owner = data;
    });
  }

  abandonAnimal(animal: any) {
    this.service.abandonAnimal(animal.id).subscribe(() => {
      this.service.abandonAnimalOwner(animal.id).subscribe(() => {
      });
    });
    this.animals.splice(this.animals.indexOf(animal), 1);
  }
}
