import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostComment } from 'src/app/models/postComment';
import { PostCommentService } from 'src/app/services/postComment.service';
@Component({
  selector: 'app-postComment-list',
  templateUrl: './postComment-list.component.html',
  styleUrls: ['./postComment-list.component.css']
})
export class PostCommentListComponent implements OnInit {

  selected = null;
  postComments: PostComment[] = [];



  constructor(
    private cmntSvc: PostCommentService,
    private route: ActivatedRoute,
    private router: Router
  ) { }
  ngOnInit(): void {
    // this.loadGames();
    let id = this.route.snapshot.paramMap.get('id');
    if(id){
      this.cmntSvc.show(id).subscribe(
        game => {
          this.selected = game;


        },
        fail =>{
          console.error("TodoListComponent.ngOnInIt(): todo retrieve failed");
          console.error(fail);
          this.router.navigateByUrl('notFound');

        }
      )
    }
    this.reload();
  }

reload() {
  this.cmntSvc.index().subscribe(
    data => {
      this.postComments= data;
    },
    err => {
      console.log('Error loading todos: ' + err);
    }
  );
  }
}
