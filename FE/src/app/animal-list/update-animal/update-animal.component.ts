import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Animal} from "../animal-list.component";
import {DataService} from "../../service/data.service";

@Component({
  selector: 'app-update-animal',
  templateUrl: './update-animal.component.html',
  styleUrls: ['./update-animal.component.css']
})
export class UpdateAnimalComponent {

  animal!:Animal

  id!:number

  constructor(
    private service:DataService,
    private route:ActivatedRoute,
    private router:Router
  ) {
  }
  ngOnInit(){
    this.id=this.route.snapshot.params['id'];
    this.animal = new Animal(0, "", "", "", 0, 0)
    console.log("Din init update animal")
    this.service.retrieveAnimalById(this.id).subscribe(
      response=>{
        this.animal = new Animal(response.id,response.nickname,response.animalType,response.specie,response.age,response.weight);
      }
    )
  }

  updateAnimal(id:number){
    console.log("UPDATING ANIMAL")
    this.service.updateAnimal(id,this.animal).subscribe(
      response =>{
        console.log(response);
        this.router.navigate(["animals-list"])
      }
    )
  }

}
