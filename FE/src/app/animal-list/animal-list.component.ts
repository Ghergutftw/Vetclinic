import {Component} from '@angular/core';
import {DataService} from "../service/data.service";
import {Router} from "@angular/router";
import {Animal} from "../models/Animal";

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
        this.animals=response
    })
  }

  deleteAnimal(id : number) {
    this.service.deleteAnimal(id).subscribe(
      () =>{
        this.deleteMessage = "DELETED SUCCESSFULLY"
        this.refreshPage();
        this.changeDeleteMessageInstantly()
      },() =>{
        this.deleteMessage = "AN ERROR HAS OCCURED"
      }
    )
  }

  updateAnimal(id : number) {
    this.router.navigate(["update-animal" , id])
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
