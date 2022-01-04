import {Directive, ElementRef, Input} from '@angular/core';

@Directive({
    selector: '[appPopup]',
    exportAs: "appPopup",
    host: {
        '(click)': 'displayMessage()'
    }
})
export class PopupDirective {

    @Input()
    message: string = ''

    constructor(_elementRef: ElementRef) {
        console.log("Directive bound!!!")
        console.log(_elementRef)
    }

    displayMessage(): void {
        alert(this.message)
    }

}
