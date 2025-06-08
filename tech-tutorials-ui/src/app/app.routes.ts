import {Routes} from '@angular/router';
import {IntroductionComponent} from './components/angular/introduction/introduction.component';
import {CliNewCreationComponent} from './components/angular/cli-new-creation/cli-new-creation.component';
import {PipesComponent} from './components/angular/pipes/pipes.component';
import {DirectivesComponent} from './components/angular/directives/directives.component';
import {ProjectStructureComponent} from './components/angular/project-structure/project-structure.component';
import {ComponentLifeCycleComponent} from './components/angular/component-life-cycle/component-life-cycle.component';
import {BootstrapSetupComponent} from './components/angular/bootstrap-setup/bootstrap-setup.component';
import {DataBindingComponent} from './components/angular/data-binding/data-binding.component';
import {AngularFormsComponent} from './components/angular/angular-forms/angular-forms.component';
import {AngularRoutingComponent} from './components/angular/angular-routing/angular-routing.component';
import {DependencyInjectionComponent} from './components/angular/dependency-injection/dependency-injection.component';
import {TemplatesComponent} from './components/angular/templates/templates.component';
import {
  ComponentCommunicationComponent
} from './components/angular/component-communication/component-communication.component';
import {ServicesComponent} from './components/angular/services/services.component';
import {RestApiComponent} from './components/angular/rest-api/rest-api.component';
import {
  RestApiAuthenticationComponent
} from './components/angular/rest-api-authentication/rest-api-authentication.component';
import {BrowserStorageComponent} from './components/angular/browser-storage/browser-storage.component';
import {CustomEventComponent} from './components/angular/custom-event/custom-event.component';
import {InterceptorComponent} from './components/angular/interceptor/interceptor.component';
import {InstallSetupComponent} from './components/angular/install-setup/install-setup.component';
import {RouteGuardsComponent} from './components/angular/route-guards/route-guards.component';
import {AngularTestingComponent} from './components/angular/angular-testing/angular-testing.component';
import {LintingFormattingComponent} from './components/angular/linting-formatting/linting-formatting.component';
import {JsonServerComponent} from './components/angular/json-server/json-server.component';
import {CoreComponent} from './components/spring/core/core.component';
import {SpringIntroductionComponent} from './components/spring/spring-introduction/spring-introduction.component';
import {WebComponent} from './components/spring/web/web.component';

export const routes: Routes = [
  {path: '', redirectTo: '/angular/introduction', pathMatch: 'full'},

  // Angular Topics
  {path: 'angular/introduction', component: IntroductionComponent},
  {path: 'angular/setup', component: InstallSetupComponent},
  {path: 'angular/cli', component: CliNewCreationComponent},
  {path: 'angular/ps', component: ProjectStructureComponent},
  {path: 'angular/clc', component: ComponentLifeCycleComponent},
  {path: 'angular/bs', component: BootstrapSetupComponent},
  {path: 'angular/binding', component: DataBindingComponent},
  {path: 'angular/forms', component: AngularFormsComponent},
  {path: 'angular/routing', component: AngularRoutingComponent},
  {path: 'angular/di', component: DependencyInjectionComponent},
  {path: 'angular/pipes', component: PipesComponent},
  {path: 'angular/directives', component: DirectivesComponent},
  {path: 'angular/templates', component: TemplatesComponent},
  {path: 'angular/cc', component: ComponentCommunicationComponent},
  {path: 'angular/services', component: ServicesComponent},
  {path: 'angular/api', component: RestApiComponent},
  {path: 'angular/authentication', component: RestApiAuthenticationComponent},
  {path: 'angular/rg', component: RouteGuardsComponent},
  {path: 'angular/storages', component: BrowserStorageComponent},
  {path: 'angular/cv', component: CustomEventComponent},
  {path: 'angular/at', component: AngularTestingComponent},
  {path: 'angular/lf', component: LintingFormattingComponent}, // Assuming Linting & Formatting is part of testing for now
  {path: 'angular/interceptor', component: InterceptorComponent},
  {path: 'angular/js', component: JsonServerComponent},
  //Spring Topics
  {path: 'spring/core', component: CoreComponent},
  {path: 'spring/introduction', component: SpringIntroductionComponent},
  {path: 'spring/web', component: WebComponent},
  {path: '**', redirectTo: '/angular/introduction'} // Wildcard route
];
