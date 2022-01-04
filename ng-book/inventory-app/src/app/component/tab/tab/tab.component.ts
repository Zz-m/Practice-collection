import {Component, Input, OnInit} from '@angular/core';

@Component({
    selector: 'app-tab',
    templateUrl: './tab.component.html',
    styleUrls: ['./tab.component.css']
})
export class TabComponent implements OnInit {

    @Input()
    title: string = '';
    active: boolean = false;
    name: string = ''

    constructor() {
    }

    ngOnInit(): void {
    }

}
