import { Category } from "./category";
import { Store } from "./store";
import { User } from "./user";

export class Meeting {
  id: number;
  name: string;
  description: string;
  createdAt: string;
  updatedAt: string;
  enabled: boolean;
  flagged: boolean;
  store: Store;
  user: User;
  category: Category;

  constructor(
    id?: number,
    name?: string,
    description?: string,
    createdAt?: string,
    updatedAt?: string,
    enabled?: boolean,
    flagged?: boolean,
    store?: Store,
    user?: User,
    category?: Category,
  ) {
  this.id = id;
  this.name = name;
  this.description = description;
  this.createdAt = createdAt;
  this.updatedAt = updatedAt;
  this.enabled = enabled;
  this.flagged = flagged;
  this.store = store;
  this.user = user;
  this.category = category;
  }
}
