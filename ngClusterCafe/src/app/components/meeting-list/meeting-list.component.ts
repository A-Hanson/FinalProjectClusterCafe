import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from 'src/app/models/category';
import { Meeting } from 'src/app/models/meeting';
import { User } from 'src/app/models/user';
import { CategoryService } from 'src/app/services/category.service';
import { MeetingService } from 'src/app/services/meeting.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-meeting-list',
  templateUrl: './meeting-list.component.html',
  styleUrls: ['./meeting-list.component.css']
})
export class MeetingListComponent implements OnInit {
  meetings: Meeting[] = [];
  selected: Meeting = null;
  categories: Category[] = [];
  currentUser: User = null;
  admin: boolean = false;
  addNewMeeting = false;
  editMeeting = false;

  constructor(
    private meetingService: MeetingService,
    private route: ActivatedRoute,
    private router: Router,
    private categoryService: CategoryService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.initialLoad();
  }

  // Methods
  displayMeeting(meeting: Meeting) {
    this.selected = meeting;
  }

  displayAll() {
    this.selected = null;
  }

  flagMeeting(meeting: Meeting) {
    meeting.flagged = true;
    this.updateMeeting(meeting);
  }

  unflagMeeting(meeting: Meeting) {
    meeting.flagged = false;
    this.updateMeeting(meeting);
  }

  setEditMeeting() {
    this.editMeeting = true;
  }

// Talking to child component
  addMeeting(newMeeting: Meeting) {
    if (newMeeting) {
      this.selected = newMeeting;
    } else {
      this.addNewMeeting = false;
    }
  }

  getUpdatedMeeting(updatedMeeting: Meeting) {
    if (updatedMeeting !== null) {
      this.editMeeting = false;
      this.addNewMeeting = false;
      this.selected = null;
      this.updateMeeting(updatedMeeting, true);
    } else {
      this.editMeeting = false;
      this.addNewMeeting = false;
      this.selected = null;
    }
  }


  // Sending Data
  updateMeeting(meeting: Meeting, display:boolean = false) {
    this.meetingService.update(meeting).subscribe(
      data => {
        if (display) {
          this.selected = data;
          this.editMeeting = false;
        }
        this.reloadMeetings();
      },
      err => {
        console.error('Error updating meeting: ' + err)
      }
    );
  }

  deleteMeeting(meeting: Meeting) {
    this.meetingService.delete(meeting.id).subscribe(
      data => {
        this.reloadMeetings();
        this.selected = null;
      },
      err => {
        console.error('Error deleting meeting: ' + err)
      }
    );
  }

  // HELPERS
  initialLoad() {
    this.reloadMeetings();
    this.reloadCategories();
    this.reloadCurrentUser();
  }

  reloadMeetings() {
    this.meetingService.index().subscribe(
      data => {
        this.meetings = data
      },
      err => {
        console.error('Error loading meetings: ' + err)
      }
    );
  }

  reloadCategories() {
    this.categoryService.index().subscribe(
      data => {this.categories = data},
      err => {console.error('Error loading categories: ' + err)}
    );
  }

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
      this.admin = true;
    } else {
      this.admin = false;
    }
  }

}
