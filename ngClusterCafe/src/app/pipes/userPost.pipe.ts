import { Pipe, PipeTransform } from '@angular/core';
import { Post } from '../models/post';

@Pipe({
  name: 'userPosts'
})
export class userPostsPipe implements PipeTransform {

  transform(posts: Post[], filterType: String): Post[] {
    var result = []

    if(filterType === 'all'){
      return posts;

    }
  for(var i = 0; i < posts.length; i++){
    for (var j = 0; j < posts[i].user.length; j++) {
      if(posts[i].user[j].name === filterType){
        result.push(posts[i]);
      }
    }
  }
  return result;
  }

}
