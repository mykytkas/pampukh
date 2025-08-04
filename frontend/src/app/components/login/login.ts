import {Component, OnInit} from '@angular/core';
import {UserAuth} from '../../dto/userDto';
import {ReactiveFormsModule, UntypedFormBuilder, UntypedFormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../services/auth-service';
import {Router, RouterLink} from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css',
  standalone: true
})
export class Login implements OnInit{

  loginForm: UntypedFormGroup

  constructor(private formBuilder: UntypedFormBuilder,
              private authService: AuthService,
              private router: Router) {
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
        this.router.navigate(['/user']);
      },
      error: err => {
        console.error(err)
      }
    })
  }
}
