import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Owner} from "../../models/Owner";
import {DataService} from "../../service/data.service";
import {Animal} from 'src/app/models/Animal';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {

  ownerId: number = 0;
  owner: Owner = new Owner();
  animals: Animal[] = [];
  ownerAnimals: number[] | undefined = [];

  constructor(
    private route: ActivatedRoute,
    private service: DataService,
    private router: Router
  ) {
  }

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
      this.ownerAnimals.forEach((animalCode) => {
        // @ts-ignore
        this.service.getAnimalByAnimalCode(animalCode).subscribe((data: Animal) => {
          if (data.animalCode) {
            data.image = this.service.getImage(data.animalCode);
            this.animals.push(data);
          }
        });
      });
    }
  }

  updateOwner() {
    this.service.updateOwner(this.ownerId, this.owner).subscribe(data => {
      this.owner = data;
    });
    this.router.navigate(['/owners']);
  }

  abandonAnimal(animal: any) {
    this.service.abandonAnimal(animal.id).subscribe(() => {
      this.service.abandonAnimalOwner(animal.id).subscribe(() => {
      });
    });
    this.animals.splice(this.animals.indexOf(animal), 1);
  }
}
