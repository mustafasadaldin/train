import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map, of } from 'rxjs';

interface infoResponse {
  age: string;
  height:string;
  weight:string;
}

@Injectable({
  providedIn: 'root'
})

export class MemberService {

    constructor(private http: HttpClient) {}

    updateInfo(age:number, height:number, weight:number): Promise<any> {
        let token;

        if(sessionStorage.getItem('token'))
        token = sessionStorage.getItem('token');

        if(localStorage.getItem('token'))
        token = localStorage.getItem('token');

        const headers = new HttpHeaders()
        .set('Authorization', `Bearer ${token}`)
        .set('Content-Type', 'application/json');

        const body = {
            age:age,
            height:height,
            weight:weight
          };
     
          return new Promise((resolve, reject) => {
            this.http.put('http://localhost:8081/users/update-info', body, { headers })
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

    getMyInfo() {

      let token;

        if(sessionStorage.getItem('token'))
        token = sessionStorage.getItem('token');

        if(localStorage.getItem('token'))
        token = localStorage.getItem('token');

        const headers = new HttpHeaders()
        .set('Authorization', `Bearer ${token}`)
        .set('Content-Type', 'application/json');

       
     
          return new Promise((resolve, reject) => {
            this.http.get<infoResponse>('http://localhost:8081/users/get-info', { headers })
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

    setTrainerRate(email:string, numberOfStars:number): Promise<any> {

      let token;

      if(sessionStorage.getItem('token'))
      token = sessionStorage.getItem('token');

      if(localStorage.getItem('token'))
      token = localStorage.getItem('token');

      const headers = new HttpHeaders()
      .set('Authorization', `Bearer ${token}`)

      const body = {
          email:email,
          numberOfStars:numberOfStars,
        };

     
      return new Promise((resolve, reject) => {
        this.http.post('http://localhost:8081/trainers/rating', body, { headers, responseType:"text" })
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

    getTrainerRate(email:string): Observable<any> {
      
      let token;

        if(sessionStorage.getItem('token'))
        token = sessionStorage.getItem('token');

        if(localStorage.getItem('token'))
        token = localStorage.getItem('token');

      const headers = new HttpHeaders()
      .set('Authorization', `Bearer ${token}`)
     
      const body = {
        email:email
      }
      return this.http.post('http://localhost:8081/trainers/rate-value', body, {headers,  responseType: 'text' })
        .pipe(
          map(
          res => {
            return(res);
            
          }
          ),catchError((error) => {
            return of('error');
          })
        );
    }
}    