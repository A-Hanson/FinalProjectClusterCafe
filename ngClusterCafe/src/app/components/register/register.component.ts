import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  // ON BACKEND STORE ID IS HARDWIRED in AuthServiceImpl
  newUser: User = new User();

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  register() {
    this.authService.register(this.newUser).subscribe(
      user => {
        console.log("User Registered!")
        this.authService.login(this.newUser.username, this.newUser.password).subscribe(
          success => {
            console.log("User Logged In!!")
            this.router.navigateByUrl('/home');
          },
          fail => {
            console.log("User unable to login: " + fail);
            this.router.navigateByUrl('/notFound');
          }
        );
        this.newUser = new User();
      },
      fail => {
        console.log("Unable to register User: " + fail);
        this.router.navigateByUrl('/notFound');
      }
    );
  }

}
