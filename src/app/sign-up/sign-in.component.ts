import { Component, ElementRef, ViewChild } from '@angular/core';
import { UserService } from '../Services/UserService';

@Component({
  selector: 'signIn',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent {
  constructor(private userService:UserService) {}

  @ViewChild('email')
  email!: ElementRef;
  @ViewChild('password')
  password!: ElementRef;
  
  signUp(event:Event) {
    event.preventDefault();
    this.userService.userSignUp(this.email.nativeElement.value, this.password.nativeElement.value)
  }
}
