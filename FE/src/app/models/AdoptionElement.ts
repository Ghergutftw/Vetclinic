
export class AdoptionElement {
  constructor(
    public id?: number,
    public animalId?: number,
    public animalCode?: string,
    public animalName?: string,
    public ownerId?: number,
    public ownerName?: string,
    public date?: Date
  ) {
  }
}
