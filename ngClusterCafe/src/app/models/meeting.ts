import { Category } from "./category";
import { Store } from "./store";
import { User } from "./user";

export class Meeting {
  id: number;
  name: string;
  description: string;
  creationDate: string;
  updatedDate: string;
  meetingDate: string;
  enabled: boolean;
  flagged: boolean;
  store: Store;
  user: User;
  category: Category = new Category();
  attendees: User[] = [];

  constructor(
    id?: number,
    name?: string,
    description?: string,
    creationDate?: string,
    updatedDate?: string,
    meetingDate?: string,
    enabled?: boolean,
    flagged?: boolean,
    store?: Store,
    user?: User,
    category?: Category,
    attendees?: User[]
  ) {
  this.id = id;
  this.name = name;
  this.description = description;
  this.creationDate = creationDate;
  this.updatedDate = updatedDate;
  this.meetingDate = meetingDate;
  this.enabled = enabled;
  this.flagged = flagged;
  this.store = store;
  this.user = user;
  this.category = category === undefined ? new Category() : category;
  this.attendees = attendees;
  }
}
