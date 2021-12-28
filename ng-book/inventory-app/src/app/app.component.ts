import {Component} from '@angular/core';
import {Product} from "./model/Product";
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
    selector: 'inventory-app',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {
    title = 'inventory-app';
    myForm: FormGroup;
    sku: AbstractControl

    constructor(fb: FormBuilder) {
        this.myForm = fb.group(
            {'sku': ['', Validators.required]}
        )
        this.sku = this.myForm.controls['sku']
    }


    onSubmit(value: any) {
        console.log("submit value:",value);
    }
}



