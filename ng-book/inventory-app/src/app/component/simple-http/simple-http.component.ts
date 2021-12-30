import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute} from "@angular/router";
import {query} from "@angular/animations";

@Component({
    selector: 'app-simple-http',
    templateUrl: './simple-http.component.html',
    styleUrls: ['./simple-http.component.css']
})
export class SimpleHttpComponent implements OnInit {
    loading: boolean = false;
    data: object = {dhx: "asd"};

    query: string = '';

    constructor(private httpClient: HttpClient, private route: ActivatedRoute) {
        route.params.subscribe(params => {
            this.query = params['query'];
            console.log("get param:", this.query)
        })
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
