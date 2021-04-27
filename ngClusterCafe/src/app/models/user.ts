import { Meeting } from "./meeting";
import { Store } from "./store";

export class User {

  id: number
  username: string;
  password: string;
  email: string;
  firstName: string;
  lastName: string;
  imgUrl: string;
  pronouns: string;
  dob: string;
  age: number;
  store: Store = new Store();
  enabled: boolean;
  role: string;
  gender: string;
  createdAt: string;
  meetings: Meeting[] = [];

constructor(
  id?: number,
  username?: string,
  password?: string,
  email?: string,
  firstName?: string,
  lastName?: string,
  imgUrl?: string,
  pronouns?: string,
  dob?: string,
  age?: number,
  store?: Store,
  enabled?: boolean,
  role?: string,
  gender?: string,
  createdAt?: string,
  meetings?: Meeting[]
  ){
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.imgUrl = imgUrl;
    this.pronouns = pronouns;
    this.dob = dob;
    this.age = age;
    this.store = store === undefined ? new Store() : store;
    this.enabled = enabled;
    this.role = role;
    this.gender = gender;
    this.createdAt = createdAt;
    this.meetings = meetings;
}
}
