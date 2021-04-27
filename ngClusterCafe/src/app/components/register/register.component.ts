import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DomSanitizer } from '@angular/platform-browser';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/auth.service';
import { Store } from 'src/app/models/store';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  // ON BACKEND STORE ID IS HARDWIRED in AuthServiceImpl
  newUser: User = new User();
  pronouns: string[] = ["He / Him", "She / Her", "They / Them", "Ze / Zir"]
  genders: string[] = ["Male", "Female", "Transgender", "Gender neutral",
                       "Non-binary", "A-gender", "Pangender",
                       "Genderqueer", "Two-spirit", "third gender"
                      ]

  newStore:Store = new Store();
  pos;
  map;
  bounds;
  infoWindow;
  currentInfoWindow;
  service;
  infoPane;

  constructor(
    private authService: AuthService,
    private router: Router,
    private sanitizer: DomSanitizer
  ) { }

  ngOnInit(): void {
    this.initMap();
  }

  initMap() {
    // Initialize variables
    this.bounds = new google.maps.LatLngBounds();
    this.infoWindow = new google.maps.InfoWindow();
    this.currentInfoWindow = this.infoWindow;
    /* standard sidebar panel */
    this.infoPane = document.getElementById('panel');
    // Browser HTML5 geolocation attempt
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          this.pos = {
            lat: position.coords.latitude,
            lng: position.coords.longitude,
          };

          map: google.maps.Map;
          this.map = new google.maps.Map(document.getElementById('map'), {
            center: this.pos,
            zoom: 15,
          });
          this.bounds.extend(this.pos);
          this.infoWindow.setPosition(this.pos);
          this.infoWindow.setContent('Location found.');
          this.infoWindow.open(this.map);
          this.map.setCenter(this.pos);
          // Call Places Nearby Search on user's location
          this.getNearbyPlaces(this.pos);
        },
        () => {
          // Browser supports geolocation, but user has denied permission
          this.handleLocationError(true, this.infoWindow);
        }
      );
    } else {
      // Browser doesn't support geolocation
      this.handleLocationError(false, this.infoWindow);
    }
  }
  // Handle a geolocation error
  handleLocationError(browserHasGeolocation, infoWindow) {
    // Set default location to Sydney, Australia
    this.pos = { lat: -33.856, lng: 151.215 };
    this.map = new google.maps.Map(document.getElementById('map'), {
      center: this.pos,
      zoom: 15,
    });
    // Display an InfoWindow at the map center
    infoWindow.setPosition(this.pos);
    infoWindow.setContent(
      browserHasGeolocation
        ? 'Geolocation permissions denied. Using default location.'
        : "Error: Your browser doesn't support geolocation."
    );
    infoWindow.open(this.map);
    this.currentInfoWindow = infoWindow;
    // Call Places Nearby Search on the default location
    this.getNearbyPlaces(this.pos);
  }

  createMarkers(places) {
  }

  // Perform a Places Nearby Search Request
  getNearbyPlaces(position) {
    let request = {
      location: position,
      rankBy: google.maps.places.RankBy.DISTANCE,
      keyword: 'sushi',
    };
    this.service = new google.maps.places.PlacesService(this.map);
    this.service.nearbySearch(request,(results, status) => {
      if (status == google.maps.places.PlacesServiceStatus.OK) {
        var places = results;
        places.forEach((place) => {
          let marker = new google.maps.Marker({
            position: place.geometry.location,
            map: this.map,
            title: place.name,
          });
          /* Click listener on map markers */
          // Add click listener to each marker
          google.maps.event.addListener(marker, 'click', () => {
            let request = {
              placeId: place.place_id,
              fields: [
                'name',
                'formatted_address',
                'geometry',
                'rating',
                'website',
                'photos',
              ],
            };
            /* Only fetches location details on user
               click in order to minimize API rate limits */
            this.service.getDetails(request, (placeResult, status) => {
              this.showDetails(placeResult, marker, status);
            });

          });
          // Adjust the map bounds to include the location of this marker
          this.bounds.extend(place.geometry.location);
        });
        /* Once all the markers have been placed, adjust the bounds of the map to
         * show all the markers within the visible area. */
        this.map.fitBounds(this.bounds);
      }
    });
  }
  // Handle the results (up to 20) of the Nearby Search

  // Set markers at the location of each place result

  /* Show place details in an info window */
  // Builds an InfoWindow to display details above the marker
  showDetails(placeResult, marker, status) {
    if (status == google.maps.places.PlacesServiceStatus.OK) {
      let placeInfowindow = new google.maps.InfoWindow();
      let rating = 'None';
      if (placeResult.rating) rating = placeResult.rating;
      placeInfowindow.setContent(
        '<div><strong>' +
          placeResult.name +
          '</strong><br>' +
          'Rating: ' +
          rating +
          '</div>'
      );
      placeInfowindow.open(marker.map, marker);
      this.currentInfoWindow.close();
      this.currentInfoWindow = placeInfowindow;
      this.showPanel(placeResult);
    } else {
      console.log('showDetails failed: ' + status);
    }
  }
  /* Load place details in a sidebar */
  // Displays place details in a sidebar
  showPanel(placeResult) {
    // If infoPane is already open, close it
    if (this.infoPane.classList.contains('open')) {
      this.infoPane.classList.remove('open');
    }
    // Clear the previous details
    while (this.infoPane.lastChild) {
      this.infoPane.removeChild(this.infoPane.lastChild);
    }
    /* Display a Place Photo with the Place Details */
    // Add the primary photo, if there is one
    if (placeResult.photos) {
      let firstPhoto = placeResult.photos[0];
      let photo = document.createElement('img');
      photo.classList.add('hero');
      photo.src = firstPhoto.getUrl();
      this.infoPane.appendChild(photo);
    }
    // Add place details with text formatting
    let name = document.createElement('h1');
    name.classList.add('place');
    name.textContent = placeResult.name;
    this.infoPane.appendChild(name);
    if (placeResult.rating) {
      let rating = document.createElement('p');
      rating.classList.add('details');
      rating.textContent = `Rating: ${placeResult.rating} \u272e`;
      this.infoPane.appendChild(rating);
    }
    let address = document.createElement('p');
    address.classList.add('details');
    address.textContent = placeResult.formatted_address;
    this.infoPane.appendChild(address);
    if (placeResult.website) {
      let websitePara = document.createElement('p');
      let websiteLink = document.createElement('a');
      let websiteUrl = document.createTextNode(placeResult.website);
      websiteLink.appendChild(websiteUrl);
      websiteLink.title = placeResult.website;
      websiteLink.href = placeResult.website;
      websitePara.appendChild(websiteLink);
      this.infoPane.appendChild(websitePara);
    }
    // Open the infoPane
    this.infoPane.classList.add('open');
  }





  register() {
    this.authService.register(this.newUser).subscribe(
      user => {
        this.authService.login(this.newUser.username, this.newUser.password).subscribe(
          success => {
            this.router.navigateByUrl('/landingPage');
          },
          fail => {
            console.log("User unable to login: " + fail);
            this.router.navigateByUrl('/notFound');
          }
        );
        this.newUser = new User();
      },
      fail => {
        console.log("Unable to register User: " + fail);
        this.router.navigateByUrl('/notFound');
      }
    );
  }
}
