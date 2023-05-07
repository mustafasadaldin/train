import { Component, Input, OnInit } from '@angular/core';
import { TrainerService } from '../Services/TrainersService';
import { MemberService } from '../Services/MemberService';

@Component({
  selector: 'trainer-profile',
  templateUrl: './trainer-profile.component.html',
  styleUrls: ['./trainer-profile.component.css']
})
export class TrainerProfileComponent implements OnInit{
  constructor(private trainerService:TrainerService, private memberService:MemberService) {}

  @Input() email:string = "";
  @Input() maxRating = 5;
  @Input() currentRating = 0;

  actualRate:number=0;
  stars: number[] = [];
  image:string="https://cdn.icon-icons.com/icons2/1378/PNG/512/avatardefault_92824.png";

ngOnInit(): void {
  this.stars = Array(this.maxRating).fill(0).map((_, index) => index + 1);
  this.trainerService.getImage(this.email).subscribe(result => {
    if(result!='error') {
      this.image = result;
    }
    
   });

   this.memberService.getTrainerRate(this.email).subscribe(result => {
    if(result!='error') {
      this.actualRate = parseFloat(result);
      this.currentRating = Math.round(this.actualRate);
    }
   })
  
 
}

async rate(rating: number){
  this.currentRating = rating;
  await this.memberService.setTrainerRate(this.email, rating);
  this.memberService.getTrainerRate(this.email).subscribe(result => {
    if(result!='error') {
      this.actualRate = parseFloat(result);
      this.currentRating = Math.round(this.actualRate);
    }
   })
}

}
