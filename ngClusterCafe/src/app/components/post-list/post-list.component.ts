import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from 'src/app/models/category';
import { Post } from 'src/app/models/post';
import { PostComment } from 'src/app/models/postComment';
import { PostCommentService } from 'src/app/services/postComment.service';
import { User } from 'src/app/models/user';
import { CategoryService } from 'src/app/services/category.service';
import { PostService } from 'src/app/services/post.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css']
})
export class PostListComponent implements OnInit {
posts: Post[] = [];
selected = null;
newPost = new Post();
addNewPost: boolean = false;
editPost = null;
categories: Category[] = [];
newPostCategory: Category = null;
postComments: PostComment[] = [];
newComment: PostComment = new PostComment();
editedComment: PostComment = null;
currentUser: User = null;
admin: boolean = false;

constructor(
  private postService: PostService,
  private route: ActivatedRoute,
  private router: Router,
  private categoryService: CategoryService,
  private userService: UserService
) { }

  ngOnInit(): void {
    let postId = this.route.snapshot.paramMap.get('id');
    if (postId) {
      this.postService.show(postId).subscribe(
        post => {
          this.selected = post;
        },
        fail => {
          console.error('PostListComponent.ngOnInit(): post retrieve failed');
          console.error(fail);
          this.router.navigateByUrl('notFound');
        }
      )
    };
    this.reload();
    this.reloadCategories();
    this.loadCurrentUser();
  }
// LOAD INFO
  reload() {
    this.postService.index().subscribe(
      data => {this.posts = data},
      err => {console.error('Error: ' + err)}
    );
  }

  reloadCategories() {
    this.categoryService.index().subscribe(
      data => {this.categories = data},
      err => {console.error('Error loading categories: ' + err)}
    );
  }
  reloadComments(){
    this.postService.getCommentsForPost(this.selected.id).subscribe(
      data => {this.postComments = data},
      err => {console.error('Error loading comments for this post' + err)}
      );
  }

  loadCurrentUser() {
    this.userService.retrieveLoggedIn().subscribe(
      data => {
        this.currentUser = data
        this.checkForAdmin();
      },
      err => {console.error('Error loading current user: ' + err)}
    );
  }

  checkForAdmin() {
    if (this.currentUser.role === 'admin') {
      this.admin = true;
    } else {
      this.admin = false;
    }
  }
// DISPLAY
  displayPost(post) {
    this.selected = post;
    this.reloadComments();
      }

  displayTable(): void {
    this.selected = null;
  }
  cancelAddPost() {
    this.newPost = new Post();
    this.addNewPost = false;
  }

  addPost(): void {
    console.log(this.newPost);

    this.postService.create(this.newPost).subscribe(
      data => {
        this.newPost = new Post();
        this.addNewPost = false;
        this.reload();
      },
      err => {
        console.error('Error: ' + err);
      }
    );
  }

  setEditPost() {
    this.editPost = Object.assign({}, this.selected);
  }

  updatePost(editedPost: Post, displayPost = true): void {
    this.postService.update(editedPost).subscribe(
      data => {
        if(displayPost) {
          this.selected = editedPost;
        }
          this.editPost = null;
          this.reload();
      },
      err => {
        console.error('Error: ' + err);
      }
    );
  }

  flagPost(post: Post) {
    post.flagged = true;
    this.updatePost(post, false);
  }

  unflagPost(post: Post) {
    post.flagged = false;
    this.updatePost(post, false);
  }

  deletedPost(id: number): void {
    this.postService.delete(id).subscribe(
      data => {
        this.selected = null;
        this.reload();
      },
      err => {
        console.error('Error: ' + err);
      }
    );
  }

  addComment(id: number) {

    this.postService.addCommentForPost(this.selected.id, this.newComment).subscribe(
      data => {
        this.newComment = new PostComment();
        this.reloadComments();
      },
      err => {
        console.error('Error: ' + err);
      }
    );
  }
  setEditComment(comment: PostComment) {
    this.editedComment = Object.assign<PostComment, PostComment>(new PostComment(), comment);
  }

  cancelEditComment() {
    this.editedComment = null;
  }

  editComment(comment: PostComment) {
    this.postService.editCommentForPost(comment.post.id, comment.id, comment).subscribe(
      data => {
        this.editedComment = null;
        this.reloadComments();
      },
      err => {
        console.error('Error editing comment: ' + err);
      }
    );
  }

  flagComment(comment: PostComment) {
    comment.flagged = true;
    this.editComment(comment);
  }

  unflagComment(comment: PostComment) {
    comment.flagged = false;
    this.editComment(comment);
  }

  deleteComment(comment: PostComment) {
    this.postService.deleteCommentForPost(comment.post.id, comment.id).subscribe(
      data => {
        this.reloadComments();
      },
      err => {
        console.error('Error deleting comment: ' + err);
      }
    );
  }

}
