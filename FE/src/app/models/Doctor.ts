import {User} from "./User";

export class Doctor{
  constructor(
    public id: number,
    public firstName: string,
    public lastName: string,
    public speciality: string,
    public age: number,
    public yearsOfExperience: number,
    public user: User
  ){
  }

}
