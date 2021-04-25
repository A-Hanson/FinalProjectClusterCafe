import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from 'src/app/models/category';
import { Post } from 'src/app/models/post';
import { PostComment } from 'src/app/models/postComment';
import { User } from 'src/app/models/user';
import { CategoryService } from 'src/app/services/category.service';
import { PostService } from 'src/app/services/post.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  posts: Post[] = [];
  selectedPost: Post = null;
  categories: Category[] = [];
  postComments: PostComment[] = [];
  selectedComment: PostComment = null;
  currentUser: User = null;

  constructor(
    private postService: PostService,
    private route: ActivatedRoute,
    private router: Router,
    private categoryService: CategoryService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.reloadCurrentUser();
  }

  selectPost(post: Post) {
    this.selectedPost = post;
  }

  unflagPost(post: Post) {
    post.flagged = false;
    this.postService.update(post).subscribe(
      data => {
        this.loadFlaggedPosts();
      },
      err => {
        console.error('Error in unflagPost()')
        console.error('Error unflagging post for admin' + err)
      }
    )
  }

  deletePost(post: Post) {
    this.postService.delete(post.id).subscribe(
      data => {
        this.loadFlaggedPosts();
      },
      err => {
        console.error('Error in deletePost()')
        console.error('Error deleteing post for admin' + err)
      }
    )
  }

  selectPostComment(comment: PostComment){
    this.selectedComment = comment;
  }

  unflagPostComment(comment: PostComment) {
    comment.flagged = false;
    this.postService.editCommentForPost(comment.post.id, comment.id, comment).subscribe(
      data => {
        this.loadFlaggedPostComments();
      },
      err => {
        console.error('Error in unflagPostComment()')
        console.error('Error unflagging postComment for admin' + err)
      }
    );
  }

  deletePostComment(comment: PostComment) {
    this.postService.deleteCommentForPost(comment.post.id, comment.id).subscribe(
      data => {
        this.loadFlaggedPostComments();
      },
      err => {
        console.error('Error in deletePostComment()')
        console.error('Error deleting postComment for admin' + err)
      }
    );
  }

// Helpers
  reloadCurrentUser() {
    this.userService.retrieveLoggedIn().subscribe(
      data => {
        this.currentUser = data;
        console.log("user loaded")
        this.checkForAdmin();
      },
      err => {console.error('Error loading current user: ' + err)}
    );
  }

  checkForAdmin() {
    if (this.currentUser.role === 'admin') {
      console.log("user is an admin")
      this.loadInitial()
    } else {
      this.router.navigateByUrl('notFound');
    }
  }

  loadInitial() {
    this.loadFlaggedPosts();
    this.loadFlaggedPostComments();
  }

  loadFlaggedPosts() {
    this.postService.indexFlagged().subscribe(
      data => {
        this.posts = data
      },
      err => {
        console.error('Error in loadFlaggedPosts()')
        console.error('Error loading flagged posts for admin' + err)
      }
    )
  }

  loadFlaggedPostComments() {
    this.postService.indexFlaggedComments().subscribe(
      data => {
        this.postComments = data
      },
      err => {
        console.error('Error in loadFlaggedPostComments()')
        console.error('Error loading flagged comments for admin' + err)
      }
    )
  }

}
