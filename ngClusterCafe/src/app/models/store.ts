export class Store {
  id: number;
  name: string;
  address: string;
  city: string;
  state: string;
  postalCode: number;
  latitude: number;
  longitude: number;
  category: string;
  enabled: boolean;

  constructor(
    id?: number,
    name?: string,
    address?: string,
    city?: string,
    state?: string,
    postalCode?: number,
    latitude?: number,
    longitude?: number,
    category?: string,
    enabled?: boolean
    ){
  this.id = id;
  this.name = name;
  this.address = address;
  this.city = city;
  this.state = state;
  this.postalCode = postalCode;
  this.latitude = latitude;
  this.longitude = longitude
  this.category = category;
  this.enabled = enabled;
}

}
