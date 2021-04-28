import { Pipe, PipeTransform } from '@angular/core';
import { Post } from '../models/post';
import { User } from '../models/user';

@Pipe({
  name: 'userPosts'
})
export class userPostsPipe implements PipeTransform {
  transform(posts: Post[], currentUser: User): Post[] {
    let filtered: Post[] = [];
      posts.forEach( (post) => {
        if (post.user.id === currentUser.id) {
          filtered.push(post);
        }
      });
    return filtered;
  }

}
