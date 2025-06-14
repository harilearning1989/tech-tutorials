import {Component, OnInit} from '@angular/core';
import {RouterLink, RouterLinkActive} from '@angular/router';
import {NgForOf} from '@angular/common';
import {TechnologiesService} from '../../services/technologies.service';
import {Technology} from '../../models/technology';

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

  technologies: Technology[] = [];
  selectedTechnology?: Technology;

  constructor(private technologiesService: TechnologiesService) {}

  ngOnInit() {
    this.technologiesService.getTechnologies().subscribe(data => {
      this.technologies = data;
    });
  }

  getTechnology(id: string) {
    this.technologiesService.getTechnologyById(id).subscribe(data => {
      this.selectedTechnology = data;
    });
  }

  addTechnology(tech: Technology) {
    this.technologiesService.addTechnology(tech).subscribe(newTech => {
      this.technologies.push(newTech);
    });
  }

  updateTechnology(id: string, tech: Technology) {
    this.technologiesService.updateTechnology(id, tech).subscribe(updatedTech => {
      // Update the local array or handle as needed
    });
  }

}
