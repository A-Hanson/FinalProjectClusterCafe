import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, tap } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { environment } from "src/environments/environment";
import { Category } from '../models/category';
@Injectable({
  providedIn: 'root',
})
export class CategoryService {

  private url = environment.baseUrl + 'api/posts';

  index() {
    return this.http.get<Category[]>(this.url + '?sorted=true').pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Error retrieving categories');
      })
    );
  }
  create(category: Category) {
    return this.http.post<Category>(this.url, category).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('KABOOM');
      })
    );
  }
  constructor(private http: HttpClient) {}
}
