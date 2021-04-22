import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostCommentListComponent } from './postComment-list.component';

describe('CommentListComponent', () => {
  let component: PostCommentListComponent;
  let fixture: ComponentFixture<PostCommentListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PostCommentListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PostCommentListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
