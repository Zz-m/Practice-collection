import {Component, OnInit} from '@angular/core';
import {SpotifySearchResult} from "../../model/spotify/SpotifySearchResult";
import {SpotifyService} from "../../service/spotify.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
    selector: 'app-music-search',
    templateUrl: './music-search.component.html',
    styleUrls: ['./music-search.component.css']
})
export class MusicSearchComponent implements OnInit {

    query: string = "";
    results: SpotifySearchResult[] = [];

    constructor(private spotifyService: SpotifyService,
                private router: Router,
                private route: ActivatedRoute) {
        this.route.queryParams
            .subscribe(params => {
                this.query = params['query'] || ''
            })
    }

    ngOnInit(): void {
        this.search();
    }

    submit(value: string) {
        this.router.navigate(['search'], {queryParams: {query: value}})
            .then(_ => this.search())
    }

    search() {
        console.log('this.query', this.query, " end")
        if (!this.query) return
        this.spotifyService.searchByTrack(this.query)
            .subscribe(res => this.renderResults(res))
    }

    private renderResults(res: any): void {
        // this.results = null;
        if (res && res.tracks && res.tracks.items) {
            this.results = res.tracks.items;
        }
    }
}
