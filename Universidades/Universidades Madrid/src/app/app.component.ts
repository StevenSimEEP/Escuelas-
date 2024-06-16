import { Component } from '@angular/core';
import { HomeComponent } from './home/home.component';
import { RouterLink, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    HomeComponent,
    RouterLink,
    RouterOutlet,
  ],
  template: `
  <main>
    <a [routerLink]="['/']">
      <header class="brand-name">
        <img class="brand-logo" src="https://images.vexels.com/media/users/3/166241/isolated/preview/204f77444fd45fe44c2fcdb549a962d6-icono-de-edificio-de-la-universidad-clasica.png" alt="logo" aria-hidden="true">
        <h1 class="title">Universidades de Madrid</h1>
      </header>
    </a>
    <section class="content">
      <router-outlet></router-outlet>
    </section>
  </main>
`,
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'universidades';
}
