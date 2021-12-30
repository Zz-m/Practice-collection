import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {catchError, Observable} from "rxjs";
import {SpotifySearchResult} from "../model/spotify/SpotifySearchResult";

@Injectable({
    providedIn: 'root'
})
export class SpotifyService {

    constructor(private httpClient: HttpClient) {
    }


    searchByTrack(query: string): Observable<SpotifySearchResult> {
        let params: string = [
            `q=${query}`,
            `type=track`,
        ].join('&')
        let queryUrl: string = `https://api.spotify.com/v1/search?${params}`
        return this.httpClient.get<SpotifySearchResult>(queryUrl);
    }


}
