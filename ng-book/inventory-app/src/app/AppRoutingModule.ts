import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {YoutubeSearchComponent} from "./component/youtube-search/youtube-search.component";
import {SimpleHttpComponent} from "./component/simple-http/simple-http.component";
import {MusicSearchComponent} from "./pages/music-search/music-search.component";
import {MusicArtistsComponent} from "./pages/music-artists/music-artists.component";
import {MusicAlbumsComponent} from "./pages/music-albums/music-albums.component";
import {MusicTracksComponent} from "./pages/music-tracks/music-tracks.component";
import {ProtectedComponent} from "./component/protected/protected.component";
import {LoggedInGuard} from "./guards/LoggedIn.guard";

const routes: Routes = [
    {path: "", redirectTo: 'simple-http/asd', pathMatch: 'full'},
    {path: "youtube", component: YoutubeSearchComponent},
    {path: "simple-http/:query", component: SimpleHttpComponent},
    {path: "search", component: MusicSearchComponent},
    {path: "artists/:id", component: MusicArtistsComponent},
    {path: "albums/:id", component: MusicAlbumsComponent},
    {path: "tracks/:id", component: MusicTracksComponent},
    {path: "protected", component: ProtectedComponent, canActivate: [LoggedInGuard]},
];

@NgModule(
    {
        imports: [RouterModule.forRoot(routes)],
        exports: [RouterModule]
    }
)
export class AppRoutingModule {
}
