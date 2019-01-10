import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {User} from '../models/user.model';
import {Observable} from "rxjs";


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class UserListService {

  constructor(private http:HttpClient) {}

  private userUrl = 'http://localhost:8088';

  public getAllUsers() {
    return this.http.get<User[]>(this.userUrl.concat('/users'));
  }
  public addUser(user: Object): Observable<Object> {
    return this.http.post(this.userUrl.concat('/user'), user);
  }

}
