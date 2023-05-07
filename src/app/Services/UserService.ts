import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map, of } from 'rxjs';

interface SignInResponse {
  token: string;
  logged:string;
}

interface SignUpResponse {
  token: string;
}

@Injectable({
  providedIn: 'root'
})

export class UserService {
    flag:boolean=false;
    retrieveResonse: any;
    base64Data: any;
    constructor(private http: HttpClient) {}

    validateUser(email: string, password: string, rememberMe: boolean): Observable<boolean> {
      const body = {email: email, password: password};
      return this.http.post<SignInResponse>('http://localhost:8081/signin', body, { responseType: 'json' })
        .pipe(
          map((response) => {
            if(!rememberMe) {
            sessionStorage.setItem('token', response.token);
            sessionStorage.setItem('logged', response.logged);
            }
            if(rememberMe)  {
            localStorage.setItem('token', response.token);
            localStorage.setItem('logged', response.logged);
            }
            return false;
          }),
          catchError((error) => {
            console.log(error.error);
            return of(true);
          })
        );
    }

    userSignUp(email:string, password:string) {
      const body = {email: email, password: password, level:"user"};
      this.http.post<SignUpResponse>('http://localhost:8081/signup', body, { responseType: 'json' }).subscribe((response) => {
        sessionStorage.setItem('token', response.token);
      }, (error)=>{
        console.log(error);
      })
    }

    forgetPassword(email: string): Promise<string> {
      const body = {email: email};
      return new Promise((resolve, reject) => {
        this.http.post('http://localhost:8081/forget-password', body, { responseType: 'text' })
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

    verifyCode(email:string, pin:string): Promise<string>{
      const body = {email: email, pin: parseInt(pin)};
      
      return new Promise((resolve, reject) => {
        this.http.post('http://localhost:8081/code-verification', body, { responseType: 'text' })
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

    changePassword(email:string, password:string): Promise<any> {
      const body = {email: email, password: password};
      
      return new Promise((resolve, reject) => {
        this.http.post('http://localhost:8081/change-password', body, { responseType: 'json' })
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

    onUpload(selectedFile:any): Promise<any> {
      console.log(selectedFile);
      
      //FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests.
      const uploadImageData = new FormData();
      uploadImageData.append('imageFile', selectedFile, selectedFile.name);
      
        let token;

        if(sessionStorage.getItem('token'))
        token = sessionStorage.getItem('token');

        if(localStorage.getItem('token'))
        token = localStorage.getItem('token');

      const headers = new HttpHeaders()
      .set('Authorization', `Bearer ${token}`)

     return new Promise((resolve, reject) => {
      this.http.post('http://localhost:8081/users/upload-image', uploadImageData, { headers, responseType: 'text' })
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

    getImage(): Observable<any> {
      
      let token;

        if(sessionStorage.getItem('token'))
        token = sessionStorage.getItem('token');

        if(localStorage.getItem('token'))
        token = localStorage.getItem('token');

      const headers = new HttpHeaders()
      .set('Authorization', `Bearer ${token}`)
     
      return this.http.get('http://localhost:8081/users/get-image', {headers,  responseType: 'arraybuffer' })
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

    getTrainersEmails(): Observable<string> {
      let token;

      if(sessionStorage.getItem('token'))
      token = sessionStorage.getItem('token');

      if(localStorage.getItem('token'))
      token = localStorage.getItem('token');

    const headers = new HttpHeaders()
    .set('Authorization', `Bearer ${token}`)
   
    return this.http.get('http://localhost:8081/trainers-usernames', {headers,  responseType: 'text' })
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



    getUsersEmails(): Observable<string> {
      let token;

      if(sessionStorage.getItem('token'))
      token = sessionStorage.getItem('token');

      if(localStorage.getItem('token'))
      token = localStorage.getItem('token');

    const headers = new HttpHeaders()
    .set('Authorization', `Bearer ${token}`)
   
    return this.http.get('http://localhost:8081/users-usernames', {headers,  responseType: 'text' })
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