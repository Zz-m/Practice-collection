import {Component, ElementRef, EventEmitter, OnInit} from '@angular/core';
import {YoutubeService} from "../../service/youtube.service";
import {SearchResult} from "../../model/SearchResult";
import {Observable, fromEvent} from "rxjs";
import {map, filter, debounceTime} from "rxjs/operators";

export var YOUTUBE_API_KEY = 'AIzaSyDcLi3_nxlmHh_RdKZ4gajD5nJod1vVY_Y'
export var YOUTUBE_API_URL = 'https://www.googleapis.com/youtube/v3/search'

let loadingGif: string = ((<any>window).__karma__) ? '' : ('images/loading.gif');

@Component({
    selector: 'app-youtube-search',
    templateUrl: './youtube-search.component.html',
    styleUrls: ['./youtube-search.component.css']
})
export class YoutubeSearchComponent implements OnInit {

    constructor() {
    }

    ngOnInit(): void {
    }


}

export var youTubeServiceInjectables: Array<any> = [
    {provide: YoutubeService, useClass: YoutubeService},
    {provide: YOUTUBE_API_KEY, useValue: YOUTUBE_API_KEY},
    {provide: YOUTUBE_API_URL, useValue: YOUTUBE_API_URL},
]


@Component({
    outputs: ['loading', 'results'],
    selector: 'search-box',
    template: `
        <input type="text" class="form-control" placeholder="Search" autofocus>
    `
})
export class SearchBox implements OnInit {
    loading: EventEmitter<boolean> = new EventEmitter<boolean>();
    results: EventEmitter<SearchResult[]> = new EventEmitter<SearchResult[]>();

    constructor(private youtube: YoutubeService,
                private el: ElementRef) {
    }

    ngOnInit(): void {
// convert the `keyup` event into an observable stream
        fromEvent(this.el.nativeElement, 'keyup')
            .pipe(map((e: any) => e.target.value),
                filter((text: string) => text.length > 1)
                , debounceTime(250)

                , map((query: string) => this.youtube.search(query))

            );
    }
}
