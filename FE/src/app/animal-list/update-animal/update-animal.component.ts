import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {DataService} from "../../service/data.service";
import {Animal} from "../../models/Animal";

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
    this.service.retrieveAnimalById(this.id).subscribe(
      response=>{
        this.animal = new Animal(response.id,response.nickname,response.animalType,response.specie,response.age,response.weight);
      }
    )
  }

  updateAnimal(id:number){
    this.service.updateAnimal(id,this.animal).subscribe(
      () =>{
        this.router.navigate(["animals-list"])
      }
    )
  }

}
