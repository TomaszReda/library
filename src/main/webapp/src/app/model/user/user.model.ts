import {UserRoles} from "./user.roles.model";
import {UserMenager} from "./userMenager.model";

export class User {
  email?: string;
  firstname?: string;
  id?: string;
  lastname?: string;
  phoneNumber?: number;
  password?: string;
  userRoles?: Array<UserRoles>;
  userMenager?: UserMenager;
  resetPasswordToken?: string;
}
