import {Album} from "./Album";
import {Artist} from "./Artist";

export class SpotifySearchResult {
    id: string = '';
    name: string = ''

    album: Album = new Album();
    artist: Artist[] = []
}
