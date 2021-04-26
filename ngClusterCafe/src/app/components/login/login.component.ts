import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username: string = null;
  password: string = null;

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  login() {
    this.authService.login(this.username, this.password).subscribe(
      success => {
        this.reset();
        this.router.navigateByUrl('/landingpage');
      },
      fail => {
        console.log("Could not log user in: " + fail);
        this.router.navigateByUrl('/notfound');
      }
    );
  }

  reset() {
    this.username = null;
    this.password = null;
  }
}
