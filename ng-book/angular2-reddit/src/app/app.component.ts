import {Component} from '@angular/core';
import {Article} from "./article/article.model";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {
    title = 'angular2-reddit';
    articles: Article[];

    constructor() {
        this.articles = [
            new Article('Angular 2', 'https://angular.io', 3),
            new Article('Fullstack', 'https://fullstack.io', 2),
            new Article('Angular Homepage', 'https://angular.io', 1),
        ]
    }

    addArticle(newTitle: HTMLInputElement, newLink: HTMLInputElement) {
        console.log(`Adding article title: ${newTitle.value} and link: ${newLink.value}`);
        this.articles.push(new Article(newTitle.value, newLink.value, 0));
        newTitle.value = '';
        newLink.value = '';
        return false;
    }

    sortedArticles():Article[] {
        return this.articles.sort((a: Article, b: Article)=>{
            return b.votes - a.votes
        })
    }
}
