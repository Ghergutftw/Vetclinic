import {Component, OnInit} from '@angular/core';
import {Consultation} from "../../models/Consultation";
import {DataService} from "../../service/data.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Doctor} from "../../models/Doctor";
import {Animal} from "../../models/Animal";
import {Owner} from "../../models/Owner";

@Component({
  selector: 'app-update',
  templateUrl: './update-consultation.component.html',
  styleUrls: ['./update-consultation.component.css']
})
export class UpdateConsultationComponent implements OnInit {

  consultation: Consultation = new Consultation();
  id: number = 0;
  speciesOptions: string[] = [];
  doctors: Doctor[] = [];
  lastNames: string[] = [];
  consultatedAnimal: Animal = new Animal();
  owner: Owner = new Owner();
  image!: File;
  imagePreview: any;
  retrievedImage: any;


  diagnostics: string[] = [];
  recommendations: string[] = [];
  treatments: string[] = [];

  constructor(public dataService: DataService,
              public router: Router,
              public activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.params['id'];

    this.dataService.getConsultationById(this.id).subscribe(
      response => {
        this.consultation = response;
        this.consultation.diagnostic = response.diagnostic;
        this.dataService.getDoctorById(response.doctorId).subscribe(
          value => {
            this.consultation.doctorLastName = value.lastName;
          }
        )

        this.dataService.getAnimalById(response.animalId).subscribe(
          value => {
            this.consultation.consultatedAnimal = value;
            console.log(value)
            if (value.animalCode) {
              this.dataService.getImage(value.animalCode).subscribe(
                value => {
                  this.retrievedImage = value;
                }
              );
            }
          }
        )

        this.dataService.getOwnerById(response.ownerId).subscribe(
          value => {
            this.owner = value;
          }
        )
      }
    )

    if (this.id) {
      this.dataService.getAnimalById(this.id).subscribe(
        response => {
          this.consultatedAnimal = response;
          this.updateSpecies();
        }
      )
    }
    this.dataService.getAllDoctors().subscribe(
      response => {
        this.doctors = response;
      }
    )

    this.dataService.getLastNames().subscribe(
      response => {
        this.lastNames = response;
      }
    )


  }

  updateSpecies() {
    switch (this.consultatedAnimal.animalType) {
      case 'Cat':
        this.speciesOptions = ['Siamese', 'Persian', 'Maine Coon', 'Ragdoll', 'Bengal'];
        break;
      case 'Dog':
        this.speciesOptions = ['Labrador Retriever', 'German Shepherd', 'Golden Retriever', 'Bulldog', 'Poodle'];
        break;
      case 'Hamster':
        this.speciesOptions = ['Syrian', 'Dwarf Campbells Russian', 'Roborovski', 'Chinese', 'Winter White'];
        break;
      case 'Rabbit':
        this.speciesOptions = ['Holland Lop', 'Mini Rex', 'Netherland Dwarf', 'Lionhead', 'Flemish Giant'];
        break;
      default:
        this.speciesOptions = [];
        break;
    }
  }


  onFileSelected(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    if (inputElement.files && inputElement.files.length > 0) {
      this.image = inputElement.files[0];
      this.displayImagePreview();
    }
  }


  displayImagePreview(): void {
    const reader = new FileReader();
    reader.onload = (e: any) => {
      this.imagePreview = e.target.result;
    };
    reader.readAsDataURL(this.image);
  }

  updateDiagnoses() {
    switch (this.consultation.diagnostic) {
      case 'Parvovirus':
        this.consultation.treatment = 'Fluid Therapy';
        this.consultation.recommendations = 'Vaccination';
        break;
      case 'Rabies':
        this.consultation.treatment = 'Antibiotics';
        this.consultation.recommendations = 'Testing';
        break;
      case 'Feline Leukemia Virus':
        this.consultation.treatment = 'Antiviral Medication';
        this.consultation.recommendations = 'Vaccination';
        break;
      case 'Canine Distemper':
        this.consultation.treatment = 'Antiviral Medication';
        this.consultation.recommendations = 'Vaccination';
        break;
      case 'Brucellosis':
        this.consultation.treatment = 'Antibiotics';
        this.consultation.recommendations = 'Testing';
        break;
      case 'Foot-and-Mouth Disease':
        this.consultation.treatment = 'Vaccination';
        this.consultation.recommendations = 'Anti-Inflammatory Medication';
        break;
      case 'Equine Influenza':
        this.consultation.treatment = 'Vaccination';
        this.consultation.recommendations = 'Rest';
        break;
      case 'Avian Influenza':
        this.consultation.treatment = 'Supportive Care';
        this.consultation.recommendations = 'Rest';
        break;
      case 'Mastitis':
        this.consultation.treatment = 'Antibiotics';
        this.consultation.recommendations = 'Hygienic Practices';
        break;
      case 'Salmonella':
        this.consultation.treatment = 'Fluid therapy';
        this.consultation.recommendations = 'Hygienic Practices';
        break;
      case 'Equine Protozoal Myeloencephalitis':
        this.consultation.treatment = '';
        this.consultation.recommendations = '';
        break;
      case 'Bovine Respiratory Disease':
        this.consultation.treatment = '';
        this.consultation.recommendations = '';
        break;
      case 'Lyme Disease':
        this.consultation.treatment = '';
        this.consultation.recommendations = '';
        break;
      case 'Scabies':
        this.consultation.treatment = '';
        this.consultation.recommendations = '';
        break;
      case 'West Nile Virus':
        this.consultation.treatment = '';
        this.consultation.recommendations = '';
        break;
    }
  }

  updateConsultation() {
    this.dataService.updateConsultationById(this.id, this.consultation).subscribe(
      () => {
        this.router.navigate(['/consultations'])
      }
    )
  }


}
