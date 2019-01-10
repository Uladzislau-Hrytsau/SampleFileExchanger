import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {User} from '../models/user.model';
import {UserListService} from './user-list.service';

@Component({
  selector: 'app-user',
  templateUrl: './user-list.component.html',
  styles: []
})
export class UserListComponent implements OnInit {

  users: User[];

  constructor(private router: Router, private userService: UserListService) {

  }

  ngOnInit() {
    this.userService.getAllUsers()
      .subscribe(data => {
        this.users = data;
      });
  };


}
