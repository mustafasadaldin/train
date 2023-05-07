import { Component, OnInit } from '@angular/core';
import { UserService } from '../Services/UserService';
import { FilterPipe } from './EmailFilter';



@Component({
  selector: 'app-user-home-page',
  templateUrl: './user-home-page.component.html',
  styleUrls: ['./user-home-page.component.css'],
  providers: [FilterPipe]
})

export class UserHomePageComponent implements OnInit {
  names: string[]=[];
  showElement:boolean=false;
  temp:string="";
  searchText:string="";
  constructor(private userService:UserService) {}

  ngOnInit(): void {
    this.userService.getTrainersEmails().subscribe(result => {
      this.names = result.split(',');
      this.names.pop();
     });
  }

  showTrainer(name:string) {
    this.showElement=true;
    this.temp=name;
    this.searchText="";
  }

  goBack() {
    this.showElement = false; 
  }

}
