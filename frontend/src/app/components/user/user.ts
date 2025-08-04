import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth-service';
import {UserDto} from '../../dto/userDto';

@Component({
  selector: 'app-user',
  imports: [],
  templateUrl: './user.html',
  styleUrl: './user.css',
  standalone: true
})
export class User implements OnInit{

  user: UserDto = {username: ""};

  constructor(private authService: AuthService) {

  }
  ngOnInit(): void {
    this.user = this.authService.getLoggedInUser();
  }
  get username(){
    return this.user.username;
  }





}
