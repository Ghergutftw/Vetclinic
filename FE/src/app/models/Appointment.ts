import {Owner} from "./Owner";

export class Appointment {
  constructor(
    public id?: number,
    public doctorLastName?: string,
    public appointmentDate?: string,
    public doctorId?: number,
    public owner: Owner = new Owner(),
    public reason?: string,
    public status?: string
  ) {
  }
}
