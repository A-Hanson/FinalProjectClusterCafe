import { Component, OnInit, Output, EventEmitter, Input} from '@angular/core';
import { Category } from 'src/app/models/category';
import { Meeting } from 'src/app/models/meeting';
import { CategoryService } from 'src/app/services/category.service';


@Component({
  selector: 'app-new-meeting',
  templateUrl: './new-meeting.component.html',
  styleUrls: ['./new-meeting.component.css']
})
export class NewMeetingComponent implements OnInit {
  @Input() makeNew: boolean = false;
  newMeeting: Meeting = new Meeting();
  @Input() meetingToEdit: Meeting = null;
  @Input() edit: boolean = false;
  categories: Category[] = [];
  @Output() newMeetingEvent = new EventEmitter<Meeting>();
  @Output() updateMeetingEvent = new EventEmitter<Meeting>();

  constructor(
    private categoryService: CategoryService
  ) { }

  ngOnInit(): void {
    this.reloadCategories();
  }
  addMeeting() {
    this.newMeeting.category.id=1; // hard-coded for the moment
    this.addNewMeeting(this.newMeeting);
    this.newMeeting = new Meeting();
  }

  cancelAddMeeting() {
    this.newMeeting = new Meeting();
    this.addNewMeeting(null);
  }

  addNewMeeting(meeting: Meeting) {
    this.makeNew = false;
    this.newMeetingEvent.emit(meeting);
  }

  updateMeeting() {
    this.edit = false;
    this.updateMeetingEvent.emit(this.meetingToEdit);
    this.meetingToEdit = null;
  }

  cancelUpdateMeeting() {
    this.edit = false;
    this.meetingToEdit = null;
    this.updateMeetingEvent.emit(null);
  }

  // HELPERS
  reloadCategories() {
    this.categoryService.index().subscribe(
      data => {
        console.log(data);
        this.categories = data
      },
      err => {console.error('Error loading categories: ' + err)}
    );
  }

}
