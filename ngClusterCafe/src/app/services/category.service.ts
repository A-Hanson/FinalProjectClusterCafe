import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, tap } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { environment } from "src/environments/environment";
import { Category } from '../models/category';
import { AuthService } from './auth.service';
@Injectable({
  providedIn: 'root',
})
export class CategoryService {

  constructor(private http: HttpClient, private auth: AuthService) {}
  private url = environment.baseUrl + 'api/categories';

  index() {
    return this.http.get<Category[]>(this.url, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Error retrieving categories');
      })
    );
  }
  create(category: Category) {
    return this.http.post<Category>(this.url, category, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('KABOOM');
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
