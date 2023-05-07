import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { SignInComponent } from './sign-up/sign-in.component';
import { NavBarComponent } from './home/nav-bar/nav-bar.component';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { UserHomePageComponent } from './user-home-page/user-home-page.component';
import { AdminHomePageComponent } from './admin-home-page/admin-home-page.component';
import { TrainerHomePageComponent } from './trainer-home-page/trainer-home-page.component';
import { UserNavBarComponent } from './user-nav-bar/user-nav-bar.component';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { FormsModule } from '@angular/forms';
import { EmployeeSignUpComponent } from './employee-sign-up/employee-sign-up.component';
import { TrainerProfileComponent } from './trainer-profile/trainer-profile.component';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { FilterPipe } from './user-home-page/EmailFilter';


const appRouters:Routes=[
  {path:'Login', component:LoginComponent},
  {path:'SignUp', component:SignInComponent},
  {path:'', component:HomeComponent},
  {path:'UserHomePage', component:UserHomePageComponent},
  {path:'AdminHomePage', component:AdminHomePageComponent},
  {path:'TrainerHomePage', component:TrainerHomePageComponent},
  {path:'Profile', component:EditProfileComponent},
  {path:'Admin/TrainerSignUp', component:EmployeeSignUpComponent}
]

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignInComponent,
    HomeComponent,
    NavBarComponent,
    UserHomePageComponent,
    AdminHomePageComponent,
    TrainerHomePageComponent,
    UserNavBarComponent,
    EditProfileComponent,
    EmployeeSignUpComponent,
    TrainerProfileComponent,
    FilterPipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(appRouters),
    HttpClientModule,
    FormsModule,
    CommonModule, 
    MatIconModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
