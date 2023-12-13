export class Consultation {
    constructor(
        public id: number,
        public date: Date,
        public pacientCode: string,
        public doctorsLastName: string
    ) {
    }
}
