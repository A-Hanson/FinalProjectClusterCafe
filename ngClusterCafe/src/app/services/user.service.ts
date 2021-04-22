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
  showLoggedIn(): Observable<User> {
    return this.http.get<User>(this.url + '/loggedIn', this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('PostService.show(): error retrieving post: ' + err);
        return throwError(err);
      })
    );
  }

// SHOW, UPDATE, DELETE

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
