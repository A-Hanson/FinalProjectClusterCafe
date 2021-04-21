import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, throwError } from "rxjs";

import { environment } from "src/environments/environment";
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
    newPost.completed = false;
    newPost.description = '';
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

  softDelete(id: number) {
    return this.http.delete<void>(this.url + '/' + id, this.getHttpOptions()).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError('Error');
      })
    );
  }

}
