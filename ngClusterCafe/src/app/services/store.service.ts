import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth.service';
import { Observable, throwError } from "rxjs";
import { catchError, tap } from 'rxjs/operators';
import { Store } from '../models/store';

@Injectable({
  providedIn: 'root'
})
export class StoreService {
  private url = environment.baseUrl + 'api/stores';

  constructor(
    private http: HttpClient, private auth: AuthService
  ) { }

  index(): Observable<Store[]> {
    return this.http.get<Store[]>(this.url, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Store Unavailable');
      })
    );
  }

  show(StoreId): Observable<Store> {
    return this.http.get<Store>(this.url + '/' + StoreId, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('StoreService.show(): error retrieving Store: ' + err);
        return throwError(err);
      })
    );
  }

  create(newStore: Store): Observable<Store> {
    let createUrl = environment.baseUrl + 'stores'
    newStore.enabled = true;
    return this.http.post<Store>(createUrl, newStore).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Error');
      })
    );
  }

  update(updatedStore: Store): Observable<Store> {
    console.log(updatedStore);
    return this.http.put<Store>(this.url + '/' + updatedStore.id, updatedStore, this.getHttpOptions()).pipe(
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
  // indexFlagged(): Observable<Store[]> {
  //   return this.http.get<Store[]>(this.url + '/flagged', this.getHttpOptions()).pipe(
  //     catchError((err: any) => {
  //       console.log(err);
  //       return throwError('Unable to load flagged Stores for Admin');
  //     })
  //   );
  // }
}
