import {Component, Input, OnInit} from '@angular/core';
import {Article} from "./article.model";

@Component({
    selector: 'app-article',
    templateUrl: './article.component.html',
    styleUrls: ['./article.component.css'],
    host: {
        class: 'row'
    }
})
export class ArticleComponent implements OnInit {
    @Input()
    article: Article;

    constructor() {
        this.article = new Article('Default', 'https://angular.io', 10)
    }

    ngOnInit(): void {
    }

    voteUp() {
        this.article.voteUp();
        return false
    }

    voteDown() {
        this.article.voteDown()
        return false;
    }
}

class John {

}
