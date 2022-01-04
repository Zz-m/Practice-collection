import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {ProductListComponent} from './product-list/product-list.component';
import {RowItemComponent} from './component/row-item/row-item.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {SimpleHttpComponent} from './component/simple-http/simple-http.component';
import {
    SearchBox,
    YoutubeSearchComponent,
    youTubeServiceInjectables
} from './component/youtube-search/youtube-search.component';
import {AppRoutingModule} from "./AppRoutingModule";
import {MusicSearchComponent} from './pages/music-search/music-search.component';
import {MusicArtistsComponent} from './pages/music-artists/music-artists.component';
import {MusicAlbumsComponent} from './pages/music-albums/music-albums.component';
import {MusicTracksComponent} from './pages/music-tracks/music-tracks.component';
import {LoginComponent} from './pages/login/login.component';
import {ProtectedComponent} from './component/protected/protected.component';
import {AUTH_PROVIDERS} from "./service/auth.service";
import {LOGGED_IN_GUARD_PROVIDERS} from "./guards/LoggedIn.guard";
import { PopupDirective } from './component/popup/popup.directive';
import { PopupComponent } from './component/popup/popup/popup.component';

@NgModule({
    declarations: [
        AppComponent,
        ProductListComponent,
        RowItemComponent,
        SimpleHttpComponent,
        YoutubeSearchComponent,
        SearchBox,
        MusicSearchComponent,
        MusicArtistsComponent,
        MusicAlbumsComponent,
        MusicTracksComponent,
        LoginComponent,
        ProtectedComponent,
        PopupDirective,
        PopupComponent
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
        youTubeServiceInjectables,
        AUTH_PROVIDERS,
        LOGGED_IN_GUARD_PROVIDERS,
    ],
    bootstrap: [AppComponent],
    exports: []
})
export class AppModule {
}
