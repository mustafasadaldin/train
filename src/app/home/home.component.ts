import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private router:Router){}

  ngOnInit(): void {
  const token = localStorage.getItem('token');
  if(token) {
    const logged = localStorage.getItem('logged');

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
  }

  @Output() leaveHomePage = new EventEmitter<boolean>();

  SignUpClicked() {
    this.leaveHomePage.emit(true);
  }
}
