import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from 'src/app/models/category';
import { Post } from 'src/app/models/post';
import { CategoryService } from 'src/app/services/category.service';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css']
})
export class PostListComponent implements OnInit {
posts: Post[] = [];
selected = null;
title = 'ngPost';
newPost = new Post();
editPost = null;
categories: Category[] = [];
newPostCategory: Category = null;

constructor(
  private postService: PostService,
  private route: ActivatedRoute,
  private router: Router,
  private categoryService: CategoryService
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
  }

  reload() {
    this.postService.index().subscribe(
      data => {this.posts = data},
      err => {console.error('Error: ' + err)}
    );
  }

  reloadCategories() {
    this.categoryService.index().subscribe(
      data => {this.categories = data},
      err => {console.error('Error: ' + err)}
    );
  }



  getNumberOfPosts = function() {
    return this.posts.length;
  }
  displayPost(post) {
    this.selected = post;
  }
  displayTable(): void {
    this.selected = null;
  }
  addPost(): void {
    // this.newPost.category = this.newPostCategory;
    console.log(this.newPost);
    this.postService.create(this.newPost).subscribe(
      data => {
        this.newPost = new Post();
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

  deletedPost(id: number): void {
    this.postService.delete(id).subscribe(
      data => {
        this.reload();
      },
      err => {
        console.error('Error: ' + err);
      }
    );
  }

}
