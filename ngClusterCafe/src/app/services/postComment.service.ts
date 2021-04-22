import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators'
import { environment } from 'src/environments/environment';
import { PostComment } from '../models/postComment';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class PostCommentService {
  // private baseUrl = 'http://localhost:8084/';
  // private url = this.baseUrl + 'api/games';
  private url = environment.baseUrl + 'api/comments';


  constructor(
private http: HttpClient,
private auth: AuthService
  ) { }

  index(): Observable<PostComment[]>{
    return this.http.get<PostComment[]>(this.url).pipe(
      catchError((err: any) => {
        console.error('PostComment Service error retrieving comments' + err);
        return throwError(err);
      })
    )
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

  // create(game: Game) {
  //   return this.http.post<Game>(this.url, game).pipe(
  //     catchError((err: any) => {
  //       console.log(err);
  //       return throwError('KABOOM');
  //     })
  //   );
  // }
  // update(editedGame: Game) {
  //    return this.http.put<Game>(`${this.url}/${editedGame.id}`, editedGame)
  //    .pipe( catchError((err:any) => {
  //     console.log(err);
  //     return throwError('Error updating todo in service');
  //    })
  //    );
  //   }
  //   // private getHttpOptions(): object {
  //   //   const credentials = this.auth.getCredentials();

  //   //   const httpOptions = {
  //   //     headers: new HttpHeaders({
  //   //       Authorization: `Basic ${credentials}`,
  //   //       'X-Requested-With': 'XMLHttpRequest'
  //   //     })
  //   //   };
  //   //   return httpOptions
  //   // }
  //   destroy(id: number) {
  //     return this.http.delete<Game>(`${this.url}/${id}`).pipe
  //     (
  //       catchError((err:any) => {
  //          console.log(err);
  //          return throwError('Error deleting game in service');
  //       }));

  //  }
   show(id): Observable<PostComment>{
    return this.http.get<PostComment>(this.url)
    .pipe(
      catchError((err:any) => {
         console.log(err);
         return throwError('Error getting comments');
      })
     );

  }
}
