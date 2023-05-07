import { Component, ElementRef, ViewChild } from '@angular/core';
import { TrainerService } from '../Services/TrainersService';

@Component({
  selector: 'app-employee-sign-up',
  templateUrl: './employee-sign-up.component.html',
  styleUrls: ['./employee-sign-up.component.css']
})
export class EmployeeSignUpComponent {
  loading:boolean = false;
  @ViewChild('email')
  email!: ElementRef;
  constructor(private trainerService:TrainerService) {}
 

 async signUp() {
    if(this.email.nativeElement.value) {
      this.loading=true;
     await this.trainerService.hireTrainer(this.email.nativeElement.value);
     this.loading=false;
    }
    else {
      alert('you should enter email for the trainer');
    }
  }
}
