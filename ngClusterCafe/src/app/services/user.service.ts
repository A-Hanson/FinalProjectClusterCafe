import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { User } from '../models/user';
import { AuthService } from './auth.service';
import { Observable, throwError } from "rxjs";
import { catchError, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = environment.baseUrl + 'api/users';

  constructor(
    private http: HttpClient, private auth: AuthService
  ) { }

  // INDEX
  index(): Observable<User[]> {
    return this.http.get<User[]>(this.url, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('UserService.index(): error retrieving users - ' + err);
        return throwError(err);
      })
    );
  }

  retrieveLoggedIn(): Observable<User> {
    return this.http.get<User>(this.url + '/username', this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('PostService.show(): error retrieving post: ' + err);
        return throwError(err);
      })
    );
  }

// SHOW,

  update(updatedUser: User): Observable<User> {
    return this.http.put<User>(this.url + '/' + updatedUser.id, updatedUser, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Error');
      })
      );
  }


  delete(id: number) {
    return this.http.delete<void>(this.url + '/' + id, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Error');
      })
    );
  }

  private getHttpOptions() {
    const credentials = this.auth.getCredentials();
    // Send credentials as Authorization header (this is spring security convention for basic auth)
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest'
      })
    };
    return httpOptions;
  }
}
