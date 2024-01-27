export class Animal {
  constructor(
    public id?: number,
    public nickname?: string,
    public animalType?: string,
    public specie?: string,
    public age?: number,
    public weight?: number,
    public forAdoption?: boolean,
    public image?: any,
    public starred?: boolean,
    public forceAnimation?: boolean
  ) {
  }
}
