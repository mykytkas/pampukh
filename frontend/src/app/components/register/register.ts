import { Component } from '@angular/core';
import {ReactiveFormsModule, UntypedFormBuilder, UntypedFormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../services/auth-service';
import {UserAuth} from '../../dto/userDto';
import {provideRouter, Router, RouterLink} from '@angular/router';
import {routes} from '../../app.routes';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css',
  standalone: true
})
export class Register {
  formGroup: UntypedFormGroup;

  constructor(private formBuilder: UntypedFormBuilder,
              private authService: AuthService,
              private router: Router) {
    this.formGroup = formBuilder.group({
      username: ["", Validators.required],
      password: ["", Validators.required],
    })
  }

  register(event: Event) {
    if(!this.formGroup.valid) {
      console.log("register problem in ts");
      return;
    }

    const userData: UserAuth = {
      username: this.formGroup.controls['username'].value,
      password: this.formGroup.controls['password'].value
    }
    this.authService.register(userData).subscribe({
      next : value => {
        console.log("registered!")
        this.router.navigate(['/user']);
      }
    })

  }
}
