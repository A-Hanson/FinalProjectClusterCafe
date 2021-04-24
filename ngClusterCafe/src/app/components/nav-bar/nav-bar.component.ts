import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  currentUser: User = null;

  constructor(
    private authService: AuthService,
    private userService: UserService

  ) { }

  ngOnInit(): void {

    this.loadCurrentUser();

  }
  checkLogin(){
    return this.authService.checkLogin()
  }
  loadCurrentUser() {
    this.userService.retrieveLoggedIn().subscribe(
      data => {this.currentUser = data},
      err => {console.error('Error loading current user: ' + err)}
    );
  }
}
