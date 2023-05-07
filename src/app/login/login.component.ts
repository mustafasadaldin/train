import { Component, ElementRef, ViewChild } from '@angular/core';
import { UserService } from '../Services/UserService';
import { Router } from '@angular/router';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  showElement:boolean = false;
  loading:boolean = false;
  notValidCode:boolean=false;
  varifyCode:boolean=false;
  isNotMatched:boolean=false;
  isTruePinChangePassword:boolean=false;

  @ViewChild('email')
  email!: ElementRef;

  @ViewChild('password')
  password!: ElementRef;

  @ViewChild('rememberMe')
  rememberMe!: ElementRef;

  @ViewChild('code')
  code!: ElementRef;

  @ViewChild('changePass')
  changePass!: ElementRef;

  @ViewChild('confirmPass')
  confirmPass!: ElementRef;

constructor(private userService : UserService, private router : Router) {}

validateUser(event:Event) {
  event.preventDefault();

  if((!this.email.nativeElement.value) || (!this.password.nativeElement.value)) {
    alert('fill all fields !');
    return;
  }

  this.userService.validateUser(this.email.nativeElement.value, 
  this.password.nativeElement.value, 
  this.rememberMe.nativeElement.checked).subscribe(result => {
  this.showElement=result;

  if(!this.showElement) {
    sessionStorage.setItem('email', this.email.nativeElement.value);
    let logged;

    if(this.rememberMe.nativeElement.checked) {
       logged = localStorage.getItem('logged');
    }
    else {
       logged = sessionStorage.getItem('logged');
    }

    switch(logged) {

      case 'user' :
        this.router.navigate(['/UserHomePage']);
        break;

      case 'admin' :
        this.router.navigate(['/AdminHomePage']);
        break;

      case 'trainer' :
        this.router.navigate(['/TrainerHomePage']);
        break;

    }
  }
});
}

 async forgetPassword() {
  if(this.email.nativeElement.value) {
    this.loading = true;
    sessionStorage.setItem('email', this.email.nativeElement.value);
    const res = await this.userService.forgetPassword(this.email.nativeElement.value);
    if(res == "done") {
      this.loading = false;
      this.varifyCode=true;
    }
  }

  else {
    alert('Please give us your email to send a code');
  }
}

async verifyCode(event:Event) {
  event.preventDefault();
  const email = sessionStorage.getItem('email');
  const res =  await this.userService.verifyCode(email?email:"", this.code.nativeElement.value);
  if(res == "true") {
    this.notValidCode=false;
    this.isTruePinChangePassword=true;
  }
  else if(res == "false") {
    this.notValidCode=true;
    this.isTruePinChangePassword=false;
}
}

async changePassword(event:Event) {
  event.preventDefault();
  if(this.changePass.nativeElement.value == this.confirmPass.nativeElement.value) {
    this.isNotMatched=false;
    const email = sessionStorage.getItem('email');
    await this.userService.changePassword(email?email:"", this.changePass.nativeElement.value);
    this.varifyCode=false;
    this.isTruePinChangePassword=false;
    this.changePass.nativeElement.value="";
    this.confirmPass.nativeElement.value="";
  }
  else {
    this.isNotMatched=true;
  }
}
}
