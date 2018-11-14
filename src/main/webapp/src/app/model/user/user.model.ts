import {UserRoles} from "./user.roles.model";

export class User {
  email?: string;
  firstname?: string;
  id?: string;
  lastname?: string;
  phoneNumber?: number;
  password?: string;
  userRoles?: Array<UserRoles>;
}
