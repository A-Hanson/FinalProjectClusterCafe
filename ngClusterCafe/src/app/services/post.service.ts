import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, throwError } from "rxjs";
import { catchError, tap } from 'rxjs/operators';
import { environment } from "src/environments/environment";
import { Post } from "../models/post";
import { AuthService } from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient, private auth: AuthService) {}
  private url = environment.baseUrl + 'api/posts';

  index(): Observable<Post[]> {
    return this.http.get<Post[]>(this.url, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Post Unavailable');
      })
    );
  }

  show(postId): Observable<Post> {
    return this.http.get<Post>(this.url + '/' + postId, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.error('PostService.show(): error retrieving post: ' + err);
        return throwError(err);
      })
    );
  }

  create(newPost: Post): Observable<Post[]> {
    newPost.enabled = true;
    newPost.flagged = false;
    return this.http.post<Post[]>(this.url, newPost, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Error');
      })
    );
  }

  update(updatedPost: Post): Observable<Post> {
    console.log(updatedPost);
    return this.http.put<Post>(this.url + '/' + updatedPost.id, updatedPost, this.getHttpOptions()).pipe(
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
