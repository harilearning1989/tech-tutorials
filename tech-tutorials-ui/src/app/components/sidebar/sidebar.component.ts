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
        { name: 'Install&Setup', route: '/angular/setup' },
        { name: 'CLI', route: '/angular/cli' },
        { name: 'Project Structure', route: '/angular/ps' },
        { name: 'Component Life Cycle', route: '/angular/clc' },
        { name: 'Bootstrap Setup', route: '/angular/bs' },
        { name: 'Binding', route: '/angular/binding' },
        { name: 'Forms', route: '/angular/forms' },
        { name: 'Routing and Navigation', route: '/angular/routing' },
        { name: 'Dependency Injection', route: '/angular/di' },
        { name: 'Pipes', route: '/angular/pipes' },
        { name: 'Directives', route: '/angular/directives' },
        { name: 'Templates', route: '/angular/templates' },
        { name: 'Component Communication', route: '/angular/cc' },
        { name: 'Services', route: '/angular/services' },
        { name: 'Integration with Rest API', route: '/angular/api'},
        { name: 'Token-based authentication', route: '/angular/authentication'},
        { name: 'Browser storage mechanisms', route: '/angular/storages'},
        { name: 'Custom Events', route: '/angular/cv' },
        { name: 'Interceptor', route: '/angular/interceptor' },
        { name: 'Testing', route: '/angular/testing' },
        { name: 'Linting & Formatting', route: '/angular/lf' },
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
      id: 'microService',
      name: 'MicroServices',
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
      id: 'testing',
      name: 'Junit/Mockito',
      topics: [
        { name: 'Setup', route: '/oracle/setup' }
      ]
    },
    {
      id: 'oracle',
      name: 'Oracle',
      topics: [
        { name: 'Setup', route: '/oracle/setup' },
        { name: 'CRUD', route: '/oracle/crud' }
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
