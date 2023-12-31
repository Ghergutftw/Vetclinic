import {Roles} from "../users/users.component";

export class User{
  constructor(
    public email ?: string,
    public username? : string,
    public password ?: string,
    public role: Roles = Roles.DEFAULT
  ) {
  }
}
