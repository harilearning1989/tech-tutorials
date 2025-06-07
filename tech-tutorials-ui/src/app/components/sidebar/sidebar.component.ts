import {Component, OnInit} from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';
import {NgForOf} from '@angular/common';
import {TechnologiesService} from '../../services/technologies.service';

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
export class SidebarComponent implements OnInit {

  technologies: any[] = [];

  constructor(private technologiesService: TechnologiesService) {}

  ngOnInit() {
    this.technologiesService.getTechnologies().subscribe(data => {
      this.technologies = data;
    });
  }
}
