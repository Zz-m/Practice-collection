import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {ProductListComponent} from './product-list/product-list.component';
import {RowItemComponent} from './component/row-item/row-item.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import { SimpleHttpComponent } from './component/simple-http/simple-http.component';
import {
    SearchBox,
    YoutubeSearchComponent,
    youTubeServiceInjectables
} from './component/youtube-search/youtube-search.component';
import {AppRoutingModule} from "./AppRoutingModule";

@NgModule({
    declarations: [
        AppComponent,
        ProductListComponent,
        RowItemComponent,
        SimpleHttpComponent,
        YoutubeSearchComponent,SearchBox
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        AppRoutingModule
    ],
    providers: [
        youTubeServiceInjectables
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
