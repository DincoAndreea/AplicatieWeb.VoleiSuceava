import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ContainerComponent } from '../container/container.component';
import { AutentificareRoutingModule } from './AutentificareRoutingModule';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    AutentificareRoutingModule,
  ],
  exports: [],
})
export class AutentificareModule {}
