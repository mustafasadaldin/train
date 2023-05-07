import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { MemberService } from '../Services/MemberService';
import { UserService } from '../Services/UserService';

interface infoResponse {
  age: string;
  height:string;
  weight:string;
}

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})

export class EditProfileComponent implements OnInit {
  image:string="https://cdn.icon-icons.com/icons2/1378/PNG/512/avatardefault_92824.png";
  ageValue:string="";
  heightValue:string="";
  weightValue:string="";
  showInfo:boolean=false;
  @Input() maxRating = 5;
  @Input() currentRating = 0;

  actualRate:number=0;
  stars: number[] = [];
  constructor(private memberService:MemberService, private userService:UserService) {}

  ngOnInit(): void {
    this.stars = Array(this.maxRating).fill(0).map((_, index) => index + 1);
   this.userService.getImage().subscribe(result => {
    if(result!='error') {
      this.image = result;
    }
    
   });
 
   const email = sessionStorage.getItem('email');
   this.memberService.getTrainerRate(email!=null?email:"").subscribe(result => {
    if(result!='error') {
      this.actualRate = parseFloat(result);
      this.currentRating = Math.round(this.actualRate);
    }
   })

  }

  loading:boolean=false;
  selectedFile!: File;

  @ViewChild('age')
  age!: ElementRef;

  @ViewChild('height')
  height!: ElementRef;

  @ViewChild('weight')
  weight!: ElementRef;

  @ViewChild('password')
  password!: ElementRef;

  @ViewChild('cPassword')
  cPassword!: ElementRef;

  async updateInfo(event:Event) {

    event.preventDefault();
    var flag = false;

    if(this.password.nativeElement.value) {
      if(this.cPassword.nativeElement.value) {
        if(this.password.nativeElement.value == this.cPassword.nativeElement.value) {
          const email = sessionStorage.getItem('email');
          await this.userService.changePassword(email?email:"", this.password.nativeElement.value);
          flag=true;
          this.password.nativeElement.value="";
          this.cPassword.nativeElement.value="";
        }

        else {
          flag=true;
          return alert('your passwords must be matched');
        }
      }
      else {
          flag=true;
          return alert ('You should confirm your password');
      }
    }
    if(this.ifUser('user')) {
    const age = this.age.nativeElement.value?this.age.nativeElement.value:"1";
    const height = this.height.nativeElement.value?this.height.nativeElement.value:"1";
    const weight = this.weight.nativeElement.value?this.weight.nativeElement.value:"1";

   
      if(age!="1" || height!="1" || weight !="1") {
      flag = true;
      this.loading=true;
     await this.memberService.updateInfo(age, height, weight);
     this.loading=false;
      }
    }
      if(this.selectedFile) {
        flag=true;
      await  this.userService.onUpload(this.selectedFile);
      location.reload();
      }
    

    if(!flag) {
      return alert('You should fill at least one feild');
    }

  }

  onFileChanged(event:any) {
    if (event.target && event.target.files) {
    this.selectedFile = event.target.files[0];
    }
  }

 async getMyInfo() {
  if(!this.showInfo) {
     const info=await this.memberService.getMyInfo() as infoResponse;
      this.ageValue = info.age;
      this.heightValue = info.height;
      this.weightValue = info.weight;
      this.showInfo=true;
  }
 else {
  this.showInfo=false;
 }
  }

  ifUser(user:string): boolean {
    if(sessionStorage.getItem('logged')) {
      if(sessionStorage.getItem('logged') == user)
      return true;
      else
      return false;
    }
   else if(localStorage.getItem('logged')) {
      if(localStorage.getItem('logged') == user)
      return true;
      else
      return false;
    }
    else {
      return false;
    }
  }

  
}
