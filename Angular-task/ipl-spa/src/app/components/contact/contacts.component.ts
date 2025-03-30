import { Component } from '@angular/core';

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.css']
})
export class ContactsComponent {
  contacts = [
    { name: "Mumbai Indians Office", email: "mumbai@ipl.com", mobile: "9876543210", address: "123 Marine Drive", city: "Mumbai", pin: "400001", logo: "assets/images/mumbai-indians.png" },
    { name: "Chennai Super Kings Office", email: "chennai@ipl.com", mobile: "8765432109", address: "456 Anna Salai", city: "Chennai", pin: "600002", logo: "assets/images/csk-logo.png" },
    { name: "Royal Challengers Bangalore Office", email: "bangalore@ipl.com", mobile: "7654321098", address: "789 MG Road", city: "Bangalore", pin: "560001", logo: "assets/images/rcb-logo.png" },
    { name: "Kolkata Knight Riders Office", email: "kolkata@ipl.com", mobile: "6543210987", address: "12 Eden Gardens", city: "Kolkata", pin: "700001", logo: "assets/images/kkr-logo.png" },
    { name: "Delhi Capitals Office", email: "delhi@ipl.com", mobile: "5432109876", address: "56 Connaught Place", city: "Delhi", pin: "110001", logo: "assets/images/dc-logo.png" },
    { name: "Sunrisers Hyderabad Office", email: "hyderabad@ipl.com", mobile: "4321098765", address: "78 Banjara Hills", city: "Hyderabad", pin: "500034", logo: "assets/images/srh-logo.png" }
  ];

  getTeamClass(teamName: string): string {
    if (teamName.includes("Mumbai")) return "mumbai";
    if (teamName.includes("Chennai")) return "chennai";
    if (teamName.includes("Bangalore")) return "bangalore";
    if (teamName.includes("Kolkata")) return "kolkata";
    if (teamName.includes("Delhi")) return "delhi";
    if (teamName.includes("Hyderabad")) return "hyderabad";
    return "";
  }
}
