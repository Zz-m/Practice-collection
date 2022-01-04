import {AfterContentInit, Component, ContentChildren, QueryList} from '@angular/core';
import {TabComponent} from "../tab/tab.component";

@Component({
    selector: 'app-tabset',
    templateUrl: './tabset.component.html',
    styleUrls: ['./tabset.component.css']
})
export class TabsetComponent implements AfterContentInit {

    @ContentChildren(TabComponent)
    tabs: QueryList<TabComponent> = new QueryList<TabComponent>();

    constructor() {
    }

    ngAfterContentInit(): void {
        this.tabs.toArray()[0].active = true
    }

    setActive(tab: TabComponent) {
        this.tabs.toArray().forEach(t => {
            t.active = false
        });
        tab.active = true;
    }
}
