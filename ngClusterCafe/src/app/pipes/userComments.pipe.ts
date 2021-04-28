import { Pipe, PipeTransform } from '@angular/core';
import { PostComment } from '../models/postComment';
import { User } from '../models/user';

@Pipe({
  name: 'userComments'
})
export class userCommentsPipe implements PipeTransform {
  transform(comments: PostComment[], currentUser: User): PostComment[] {
    let filtered: PostComment[] = [];
      comments.forEach( (comment) => {
        if (comment.user.id === currentUser.id) {
          filtered.push(comment);
        }
      });
    return filtered;
  }

}
