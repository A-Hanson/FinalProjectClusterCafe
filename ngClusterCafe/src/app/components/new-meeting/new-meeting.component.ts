import { Component, OnInit, Output, EventEmitter} from '@angular/core';
import { Category } from 'src/app/models/category';
import { Meeting } from 'src/app/models/meeting';
import { CategoryService } from 'src/app/services/category.service';


@Component({
  selector: 'app-new-meeting',
  templateUrl: './new-meeting.component.html',
  styleUrls: ['./new-meeting.component.css']
})
export class NewMeetingComponent implements OnInit {
  newMeeting: Meeting = new Meeting();
  selected = null;
  categories: Category[] = [];
  @Output() newMeetingEvent = new EventEmitter<Meeting>();

  constructor(
    private categoryService: CategoryService
  ) { }

  ngOnInit(): void {
    this.reloadCategories();
  }
  addMeeting() {
    this.newMeeting.category.id=1; // hard-coded for the moment
    this.addNewMeeting(this.newMeeting);
  }

  cancelAddMeeting() {
    this.newMeeting = new Meeting();
    this.addNewMeeting(null);
  }

  addNewMeeting(meeting: Meeting) {
    this.newMeetingEvent.emit(meeting);
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
