import {Component, OnInit} from '@angular/core';
import {ReactiveFormsModule, UntypedFormBuilder, UntypedFormGroup, Validators} from '@angular/forms';
import {AuthService} from '../services/auth-service';
import {UserAuth} from '../dto/userDto';

@Component({
  selector: 'app-user',
  imports: [ReactiveFormsModule],
  templateUrl: './user.html',
  styleUrl: './user.css',
  standalone: true
})
export class User implements OnInit{

  loginForm: UntypedFormGroup

  constructor(private formBuilder: UntypedFormBuilder,
              private authService: AuthService) {
    this.loginForm = this.formBuilder.group({
      username: ["", [Validators.required]],
      password: ["", [Validators.required]],
    });
  }
  ngOnInit(): void {

  }

  login(event: Event) {
    if (!this.loginForm.valid){
      console.log("bad bad");
      return;
    }
    const userData: UserAuth = {
      username: this.loginForm.controls['username'].value,
      password: this.loginForm.controls['password'].value
    }
    this.authService.login(userData).subscribe({
      next: () => {
        console.log("yuhuu")
      },
      error: err => {
        console.log("nuhuu")
        console.error(err)
      }
    })
  }



}
