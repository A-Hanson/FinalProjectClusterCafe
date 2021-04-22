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
  pronouns: string[] = ["He / Him", "She / Her", "They / Them", "Ze / Zir"]
  genders: string[] = ["Male", "Female", "Transgender", "Gender neutral",
                       "Non-binary", "A-gender", "Pangender",
                       "Genderqueer", "Two-spirit", "third gender"
                      ]

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  register() {
    this.authService.register(this.newUser).subscribe(
      user => {
        this.authService.login(this.newUser.username, this.newUser.password).subscribe(
          success => {
            this.router.navigateByUrl('/landingPage');
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
