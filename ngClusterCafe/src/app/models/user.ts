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
  location: string;
  enabled: boolean;
  role: string;
  gender: string;
  createdAt: string;

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
  location?: string,
  enabled?: boolean,
  role?: string,
  gender?: string,
  createdAt?: string,
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
    this.location = location;
    this.enabled = enabled;
    this.role = role;
    this.gender = gender;
    this.createdAt = createdAt;
}
}
