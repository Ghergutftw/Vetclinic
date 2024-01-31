import {Component, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Animal} from "../../models/Animal";

@Component({
  selector: 'app-confirm-modal',
  templateUrl: './confirm-modal.component.html',
  styleUrls: ['./confirm-modal.component.css']
})
export class ConfirmModalComponent {
  @ViewChild('modal') modal: any;
  @Input() animalToAdopt?: Animal;
  @Output() onConfirm = new EventEmitter();

  constructor(private modalService: NgbModal) {}

  open() {
    this.modalService.open(this.modal, { centered: true });
  }

  closeModal() {
    this.modalService.dismissAll();
  }

  confirmAdoption() {
    this.onConfirm.emit(this.animalToAdopt);
    this.closeModal();
  }
}
