export class Owner {
  constructor(
    public id?: number,
    public email?: string,
    public firstName?: string,
    public lastName?: string,
    public phoneNumber?: string,
    public ownedAnimals?: number[]
  ) {
  }
}
