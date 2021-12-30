import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
    selector: 'app-simple-http',
    templateUrl: './simple-http.component.html',
    styleUrls: ['./simple-http.component.css']
})
export class SimpleHttpComponent implements OnInit {
    loading: boolean = false;
    data: object = {dhx: "asd"};

    constructor(private httpClient: HttpClient) {
    }

    ngOnInit(): void {
    }

    makeRequest() {
        this.loading = true;
        this.httpClient.get('https://jsonplaceholder.typicode.com/posts/1')
            .subscribe((res: any) => {
                this.data = res;
                this.loading = false;
            })
    }
}
