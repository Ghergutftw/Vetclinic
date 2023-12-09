import {Component} from '@angular/core';
import {DataService} from "../service/data.service";
import {Router} from "@angular/router";

export class Animal {
  constructor(
    public id: number,
    public nickname: string,
    public animalType: string,
    public specie: string,
    public age: number,
    public weight: number
  ) {
  }
}

@Component({
  selector: 'app-animal-list',
  templateUrl: './animal-list.component.html',
  styleUrls: ['./animal-list.component.css']
})
export class AnimalListComponent {

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
    this.service.getAllAnimals().subscribe(
      response => {
        console.log(response)
        this.animals=response
    },error => {
        console.log("An error has occured in Animal List refresh page")
      })
  }

  deleteAnimal(id : number) {
    console.log(`DELETEING ANIMAL WITH ID OF ${id}`);
    this.service.deleteAnimal(id).subscribe(
      response =>{
        console.log("DELETING")
        this.deleteMessage = "DELETED SUCCESSFULLY"
        this.refreshPage();
        this.changeDeleteMessageInstantly()
      },error =>{
        console.log("An error has occured in delete animal")
        this.deleteMessage = "AN ERROR HAS OCCURED"
      }
    )
  }

  updateAnimal(id : number) {
    console.log("UPDATING DOCTOR");
    this.router.navigate(["update-animal" , id])
  }

  createAnimal() {
    console.log("Creating animal")
  }

  changeDeleteMessageInstantly(){
    setTimeout(() =>{
      this.deleteMessage = '';
    },2000)
  }
}
