import { Component, OnInit } from '@angular/core';
import {Register} from '../../model/Register'
import { UserService } from '../../service/user.service';
@Component({
  selector: 'app-view-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  register: Register= <Register>{};

  constructor(private userService : UserService) { }

  ngOnInit() {
   
  }

  registerUser():void{
    this.userService.registerUser(this.register);
      
  }



}
