import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserListComponent} from "./user-list/user-list.component";
import {CreateUserComponent} from "./create-user/create-user.component";

const routes: Routes = [
  {path: 'users', component: UserListComponent},
  { path: 'user', component: CreateUserComponent },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class AppRoutingModule {
}
