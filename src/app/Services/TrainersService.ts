import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, catchError, map, of } from "rxjs";

@Injectable({
    providedIn: 'root'
  })
  
export class TrainerService {
    constructor(private http: HttpClient) {}
    hireTrainer(email:string) {
        let token;

        if(sessionStorage.getItem('token'))
        token = sessionStorage.getItem('token');

        if(localStorage.getItem('token'))
        token = localStorage.getItem('token');

        const headers = new HttpHeaders()
        .set('Authorization', `Bearer ${token}`)
        .set('Content-Type', 'application/json');
        
        const body = {
            email:email,
            password:"",
            level:"trainer"
          };

        return new Promise((resolve, reject) => {
            this.http.post('http://localhost:8081/trainers/hiring', body, { headers })
              .subscribe(
                (response) => {
                  resolve(response);
                },
                (error) => {
                  reject(error);
                }
              );
          });
    }

   
    getImage(email:string): Observable<any> {
      
      let token;

        if(sessionStorage.getItem('token'))
        token = sessionStorage.getItem('token');

        if(localStorage.getItem('token'))
        token = localStorage.getItem('token');

      const headers = new HttpHeaders()
      .set('Authorization', `Bearer ${token}`)

      const body ={
        email:email
      }
     
      return this.http.post('http://localhost:8081/trainers/trainer-image', body,{headers,responseType: 'arraybuffer' })
        .pipe(
          map(
          res => {
            const blob = new Blob([res], { type: 'image/png' });
            return(URL.createObjectURL(blob));
            
          }
          ),catchError((error) => {
            return of('error');
          })
        );
    }
}