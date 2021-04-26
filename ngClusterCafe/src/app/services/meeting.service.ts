import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth.service';
import { Observable, throwError } from "rxjs";
import { catchError, tap } from 'rxjs/operators';
import { Meeting } from '../models/meeting';

@Injectable({
  providedIn: 'root'
})
export class MeetingService {
  private url = environment.baseUrl + 'api/meetings';
  constructor(
    private http: HttpClient, private auth: AuthService
  ) { }

  index(): Observable<Meeting[]> {
    return this.http.get<Meeting[]>(this.url, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Meeting Unavailable');
      })
    );
  }

  show(MeetingId): Observable<Meeting> {
    return this.http.get<Meeting>(this.url + '/' + MeetingId, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('MeetingService.show(): error retrieving Meeting: ' + err);
        return throwError(err);
      })
    );
  }

  create(newMeeting: Meeting): Observable<Meeting[]> {
    newMeeting.enabled = true;
    newMeeting.flagged = false;
    return this.http.post<Meeting[]>(this.url, newMeeting, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Error');
      })
    );
  }

  update(updatedMeeting: Meeting): Observable<Meeting> {
    console.log(updatedMeeting);
    return this.http.put<Meeting>(this.url + '/' + updatedMeeting.id, updatedMeeting, this.getHttpOptions()).pipe(
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

  // ADMIN REQUESTS
  indexFlagged(): Observable<Meeting[]> {
    return this.http.get<Meeting[]>(this.url + '/flagged', this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Unable to load flagged meetings for Admin');
      })
    );
  }
}
