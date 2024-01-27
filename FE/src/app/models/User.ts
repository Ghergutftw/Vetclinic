import {Roles} from "../users/users.component";

export class User{
  constructor(
    public id ?: number,
    public email ?: string,
    public username? : string,
    public password ?: string,
    public firstName ?: string,
    public lastName ?: string,
    public phoneNumber ?: string,
    public role: Roles = Roles.DEFAULT
  ) {
  }
}
