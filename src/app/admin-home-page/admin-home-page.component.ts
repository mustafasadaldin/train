import { Component } from '@angular/core';
import { UserService } from '../Services/UserService';
import { FilterPipe } from '../user-home-page/EmailFilter';

@Component({
  selector: 'app-admin-home-page',
  templateUrl: './admin-home-page.component.html',
  styleUrls: ['./admin-home-page.component.css'],
  providers: [FilterPipe]
})
export class AdminHomePageComponent {
  names: string[]=[];
  showTrianers:boolean=false;
  showUsers:boolean=false;
  searchText:string="";
  constructor(private userService:UserService) {}


 showTrainers(): void {
   this.userService.getTrainersEmails().subscribe(result => {
      if(result != 'error') {
         this.names = result.split(',');
         this.names.pop();
         this.showTrianerList();
      }
   });
}
showTrianerList() {
  this.showTrianers=true;
  this.showUsers=false;
}

setShowUsersList() {
  this.showUsers=true;
  this.showTrianers=false;
}

goBack() {
  this.showTrianers=false;
  this.showUsers=false;
}

showUsersList() {
  this.userService.getUsersEmails().subscribe(result => {
    if(result != 'error') {
       this.names = result.split(',');
       this.names.pop();
       this.setShowUsersList();
    }
 });
}
}
