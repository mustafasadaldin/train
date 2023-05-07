import { Component } from '@angular/core';
//import { UserService } from './Services/UserService';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  isInHomePage:boolean=true;
  leaveHomePageEventHandler() {
    this.isInHomePage=false;
  }
}
