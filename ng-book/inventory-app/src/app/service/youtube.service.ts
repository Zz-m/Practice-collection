import {Inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {YOUTUBE_API_KEY, YOUTUBE_API_URL} from "../component/youtube-search/youtube-search.component";
import {Observable} from "rxjs";
import {SearchResult} from "../model/SearchResult";
import {map} from "rxjs/operators";


@Injectable({
    providedIn: 'root'
})
export class YoutubeService {

    constructor(private httpClient: HttpClient,
                @Inject(YOUTUBE_API_KEY) private apiKey: string,
                @Inject(YOUTUBE_API_URL) private apiUrl: string,) {

    }

    search(query: string): Observable<SearchResult[]> {
        let params: string = [
            'q=${query}',
            'key=${this.apiKey}',
            'part=snippet',
            'type=video',
            'maxResults=10'
        ].join('&');
        let queryUrl: string = '${this.api}?${params}'
        return this.httpClient.get(queryUrl).pipe(
            map(
                (response: any) => {
                    return (<any>response.json()).items.map(
                        (item: { id: { videoId: any; }; snippet: { title: any; description: any; thumbnailUrl: { high: { url: any; }; }; }; }) => {
                            console.log("raw data", item)
                            return new SearchResult({
                                id: item.id.videoId,
                                title: item.snippet.title,
                                description: item.snippet.description,
                                thumbnailUrl: item.snippet.thumbnailUrl.high.url,
                            });
                        }
                    )
                }
            )
        );

    }
}
