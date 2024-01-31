import {Component, OnInit} from '@angular/core';
import {Animal} from "../models/Animal";
import {DataService} from "../service/data.service";
import {animate, keyframes, state, style, transition, trigger} from "@angular/animations";
import {Adoption} from "../models/Adoption";

@Component({
  selector: 'app-adoptions',
  templateUrl: './adoptions.component.html',
  styleUrls: ['./adoptions.component.css'],
  animations: [
    trigger('slide', [
      state('void', style({transform: 'translateY(0)'})),
      state('*', style({transform: 'translateY(0)'})),
      transition(':enter', [
        animate('0.5s ease-in-out', keyframes([
          style({offset: 0, transform: 'translateY(-10%)'}),
          style({offset: 1, transform: 'translateY(0)'})
        ])),
      ]),
      transition(':leave', [
        animate('0.5s ease-in-out', keyframes([
          style({offset: 0, transform: 'translateY(0)'}),
          style({offset: 1, transform: 'translateY(10%)'})
        ])),
      ]),
    ]),],
})
export class AdoptionsComponent  implements OnInit{
  animals!: Animal[];
  exists : boolean = false;
  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.loadAnimals();
    window.addEventListener('resize', () => {
    });
  }

  loadAnimals() {
    this.dataService.getAllAnimals().subscribe((animals: Animal[]) => {
      this.animals = animals.filter((animal : Animal) => animal.forAdoption === true);

      if(animals.length > 0) {
        this.exists = true;
      }

      this.animals.forEach(animal => {
        animal.image = this.dataService.getImage(animal.id);
      });
    });
  }


  adoptAnimal(animal: Animal) {
    let user = sessionStorage.getItem('Authenticated User');
    if(user === null) {
      alert("You must be logged in to adopt an animal!");
      return;
    }
    this.dataService.createAdoption(new Adoption(user, animal.id, new Date())).subscribe(
      (response: any) => {
        this.loadAnimals();
      }
    )
  }

  starAnimal(animal: Animal) {
    animal.starred = !animal.starred;
    animal.forceAnimation = true;
    localStorage.setItem('starredAnimals', JSON.stringify(this.animals));
    this.animals.sort((a, b) => (b.starred ? 1 : 0) - (a.starred ? 1 : 0));
    setTimeout(() => {
      animal.forceAnimation = false;
    }, 500);
  }

}
