import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'app-sidebar',
  imports: [
    RouterLink,
    NgForOf,
    RouterLinkActive
  ],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {

  technologies = [
    {
      id: 'angular',
      name: 'Angular',
      topics: [
        { name: 'Introduction', route: '/angular/introduction' },
        { name: 'CLI', route: '/angular/cli' },
        { name: 'Pipes', route: '/angular/pipes' },
        { name: 'Directives', route: '/angular/directives' }
      ]
    },
    {
      id: 'java',
      name: 'Java',
      topics: [
        { name: 'Basics', route: '/java/basics' },
        { name: 'OOP', route: '/java/oop' }
      ]
    },
    {
      id: 'spring',
      name: 'Spring Boot',
      topics: [
        { name: 'Introduction', route: '/spring/introduction' },
        { name: 'Rest API', route: '/spring/rest-api' }
      ]
    },
    {
      id: 'jpa',
      name: 'JPA',
      topics: [
        { name: 'Entities', route: '/jpa/entities' },
        { name: 'Repositories', route: '/jpa/repositories' }
      ]
    },
    {
      id: 'postgres',
      name: 'PostgreSQL',
      topics: [
        { name: 'Setup', route: '/postgres/setup' },
        { name: 'CRUD', route: '/postgres/crud' }
      ]
    }
  ];


}
