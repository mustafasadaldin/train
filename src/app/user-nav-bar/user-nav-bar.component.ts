import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'user-nav-bar',
  templateUrl: './user-nav-bar.component.html',
  styleUrls: ['./user-nav-bar.component.css']
})
export class UserNavBarComponent {
  constructor(private router:Router) {}
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
logout() {

  if(sessionStorage.getItem('token')) {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('logged');
  }
 else if(localStorage.getItem('token')) {
  localStorage.removeItem('token');
  localStorage.removeItem('logged');
}
sessionStorage.removeItem('email');
this.router.navigate(['/Login']);
}
}
