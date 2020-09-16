import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button'
import { MatIconModule } from '@angular/material/icon'

const MaterialComponents = [MatButtonModule, MatIconModule]

@NgModule({
  declarations: [],
  imports: [
    MaterialComponents
  ],
  exports: [MaterialComponents]
})
export class MaterialModule { }
