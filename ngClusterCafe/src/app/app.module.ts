import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from './components/register/register.component';
import { AuthService } from './services/auth.service';
import { HomeComponent } from './components/home/home.component';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { LoginComponent } from './components/login/login.component';
import { LogoutComponent } from './components/logout/logout.component';
import { UserLandingPageComponent } from './components/user-landing-page/user-landing-page.component';
import { PostListComponent } from './components/post-list/post-list.component';
import { PostCommentListComponent } from './components/postComment-list/postComment-list.component';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { MeetingListComponent } from './components/meeting-list/meeting-list.component';
import { FlaggedPipe } from './pipes/flagged.pipe';
import { NewMeetingComponent } from './components/new-meeting/new-meeting.component';
import { CategoryService } from './services/category.service';


@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    HomeComponent,
    NavBarComponent,
    LoginComponent,
    LogoutComponent,
    UserLandingPageComponent,
    PostListComponent,
    PostCommentListComponent,
    AdminDashboardComponent,
    MeetingListComponent,
    FlaggedPipe,
    NewMeetingComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    NgbModule,
    HttpClientModule
  ],
  providers: [
    AuthService,
    CategoryService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
