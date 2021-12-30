import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {YoutubeSearchComponent} from "./component/youtube-search/youtube-search.component";
import {SimpleHttpComponent} from "./component/simple-http/simple-http.component";

const routes: Routes = [
    {path: "", redirectTo: 'simple-http/asd', pathMatch: 'full'},
    {path: "youtube", component: YoutubeSearchComponent},
    {path: "simple-http/:query", component: SimpleHttpComponent}
];

@NgModule(
    {
        imports: [RouterModule.forRoot(routes)],
        exports: [RouterModule]
    }
)
export class AppRoutingModule {
}
