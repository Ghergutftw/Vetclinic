import {Component, OnInit} from '@angular/core';
import {DataService} from "../../service/data.service";
import {Consultation} from "../../models/Consultation";
import {ActivatedRoute, Router} from "@angular/router";
import {Doctor} from "../../models/Doctor";
import {Animal} from "../../models/Animal";
import {DocsService} from "../../service/docs.service";
import {Status} from "../../Enums/Status";
import {Owner} from "../../models/Owner";
import {NotificationService} from "../../service/notification.service";

@Component({
  selector: 'app-create-consultation',
  templateUrl: './create-consultation.component.html',
  styleUrls: ['./create-consultation.component.css']
})
export class CreateConsultationComponent implements OnInit {
  doctors: Doctor[] = [];
  speciesOptions: string[] = [];
  diagnostics: string[] = [];
  recommendations: string[] = [];
  treatments: string[] = [];
  image!: File;
  imagePreview: any;
  appointmentId: number = 0;

  consultation: Consultation = new Consultation();

  response: string = "";
  email: string = "";
  owner: Owner = new Owner();

  constructor(
    public service: DataService,
    public docsService: DocsService,
    public router: Router,
    public activatedRoute: ActivatedRoute,
    public notService: NotificationService
  ) {
  }

  updateSpecies() {

    switch (this.consultation.consultatedAnimal?.animalType) {
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

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(
      params => {
        this.appointmentId = params['id']
        this.consultation.doctorLastName = params['lastName']
      }
    )
    this.service.getAllDoctors().subscribe(
      response => {
        this.doctors = response;
      }
    )

    this.service.getAppointmentById(this.appointmentId).subscribe(
      value => {
        this.owner = value.owner
      }
    )
    this.consultation.date = new Date();

    this.service.getAnimalDiseases().subscribe((data) => (this.diagnostics = data));
    this.service.getRecommendations().subscribe((data) => (this.recommendations = data));
    this.service.getTreatments().subscribe((data) => (this.treatments = data));
  }

  createConsultation() {
    let animalId = 0;
    // @ts-ignore
    this.email = JSON.parse(sessionStorage.getItem('Authenticated User')).email;

    this.service.createConsultation(this.consultation, this.owner).subscribe(
      response => {
        animalId = +response.message;
        this.service.saveImage(animalId, this.image).subscribe();
        this.service.updateStatus(this.appointmentId, Status.FINISHED).subscribe(
          () => {
            this.docsService.getReceipt(this.owner.email, this.consultation).subscribe();
            this.notService.showNotification("Consultation created successfully!");
            this.router.navigate(["/consultations"])
          }
        )
      }
    );
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

}
