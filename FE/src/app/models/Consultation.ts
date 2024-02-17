import {Animal} from "./Animal";
import {Owner} from "./Owner";

export class Consultation {
  constructor(
    public id?: number,
    public date?: Date,
    public doctorId?: number,
    public animalId?: number,
    public ownerId?: number,
    public animalCode? : string,
    public doctorLastName?: string,
    public diagnostic?: string,
    public treatment?: string,
    public recommendations?: string,
    public price?: number,
    public consultatedAnimal: Animal = new Animal(),
    public owner : Owner = new Owner(),
    public image?: File
  ) {
    }
}
