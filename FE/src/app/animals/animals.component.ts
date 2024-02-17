import {Component} from '@angular/core';
import {DataService} from "../service/data.service";
import {Router} from "@angular/router";
import {Animal} from "../models/Animal";

@Component({
  selector: 'app-animals',
  templateUrl: './animals.component.html',
  styleUrls: ['./animals.component.css']
})
export class AnimalsComponent {

  deleteMessage : string = ''

  animals: Animal[] | any

  constructor(
    public service: DataService,
    public router:Router
  ) {
  }

  ngOnInit() {
    this.refreshPage()
  }

  refreshPage() {
    this.animals = [];
    this.service.getAllAnimals().subscribe(
      response => {
        this.animals=response
    })
  }

  deleteAnimal(id : number) {
    this.service.deleteAnimal(id).subscribe(
      () =>{
        this.deleteMessage = "DELETED SUCCESSFULLY"
        this.refreshPage();
        this.changeDeleteMessageInstantly()
      }
    )
  }

  updateAnimal(animalCode : string) {
    this.router.navigate(["update-animal" , animalCode])
  }


  changeDeleteMessageInstantly(){
    setTimeout(() =>{
      this.deleteMessage = '';
    },2000)
  }

  createAnimal() {
    this.router.navigate(["create-animal"])
  }
}
