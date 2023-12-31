import {Animal} from "./Animal";

export class Consultation {
  constructor(
    public id?: number,
    public date?: Date,
    public animalCode? : string,
    public doctorLastName?: string,
    public diagnostic?: string,
    public treatment?: string,
    public recommendations?: string,
    public price?: number,
    public consultatedAnimal: Animal = new Animal()
  ) {
    }
}
