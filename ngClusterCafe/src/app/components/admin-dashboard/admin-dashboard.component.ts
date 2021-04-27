import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from 'src/app/models/category';
import { Meeting } from 'src/app/models/meeting';
import { Post } from 'src/app/models/post';
import { PostComment } from 'src/app/models/postComment';
import { User } from 'src/app/models/user';
import { CategoryService } from 'src/app/services/category.service';
import { MeetingService } from 'src/app/services/meeting.service';
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
  meetings: Meeting[] = [];
  selectedMeeting: Meeting = null;
  users: User[] = [];
  selectedUser = null;
  currentUser: User = null;

  constructor(
    private postService: PostService,
    private route: ActivatedRoute,
    private router: Router,
    private categoryService: CategoryService,
    private userService: UserService,
    private meetingService: MeetingService
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

  selectMeeting(meeting: Meeting) {
    this.selectedMeeting = meeting;
  }

  unflagMeeting(meeting: Meeting) {
    meeting.flagged = false;
    this.meetingService.update(meeting).subscribe(
      data => {
        this.loadFlaggedMeetings();
      },
      err => {
        console.error('Error in unflagMeeting()')
        console.error('Error updating Meeting for admin' + err)
      }
    )
  }

  deleteMeeting(meeting: Meeting) {
    this.meetingService.delete(meeting.id).subscribe(
      data => {
        this.loadFlaggedMeetings();
      },
      err => {
        console.error('Error in deleteMeeting()')
        console.error('Error deleting Meeting for admin' + err)
      }
    )
  }

  selectUser(user: User) {
    this.selectedUser = user;
  }

  unDeleteUser(user: User) {
    user.enabled = true;
    this.userService.update(user).subscribe(
      data => {
        this.loadUsers();
      },
      err => {
        console.error('Error in unDeleteUser()')
        console.error('Error unDeleting User for admin' + err)
      }
    )
  }

  deleteUser(user: User) {
    this.userService.delete(user.id).subscribe(
      data => {
        this.loadUsers();
      },
      err => {
        console.error('Error in deleteUser()')
        console.error('Error deleting User for admin' + err)
      }
    )
  }

// Helpers
  reloadCurrentUser() {
    this.userService.retrieveLoggedIn().subscribe(
      data => {
        this.currentUser = data;
        this.checkForAdmin();
      },
      err => {console.error('Error loading current user: ' + err)}
    );
  }

  checkForAdmin() {
    if (this.currentUser.role === 'admin') {
      this.loadInitial()
    } else {
      this.router.navigateByUrl('notfound');
    }
  }

  loadInitial() {
    this.loadFlaggedPosts();
    this.loadFlaggedPostComments();
    this.loadFlaggedMeetings();
    this.loadUsers();
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

  loadFlaggedMeetings() {
    this.meetingService.indexFlagged().subscribe(
      data => {
        this.meetings = data;
      },
      err => {
        console.error('Error in loadFlaggedMeetings()')
        console.error('Error loading flagged meetings for admin' + err)
      }
    )
  }

  loadUsers() {
    this.userService.index().subscribe(
      data => {
        this.users = data
      },
      err => {
        console.error('Error in loadUsers()');
        console.error('Error loading users for admin' + err)
      }
    );
  }

}
