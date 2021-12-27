import {Component, EventEmitter, OnInit} from '@angular/core';
import {Product} from "../model/Product";

@Component({
    selector: 'app-product-list',
    templateUrl: './product-list.component.html',
    styleUrls: ['./product-list.component.css'],
    inputs:["products"],
    outputs:["onProductSelected"]
})
export class ProductListComponent implements OnInit {

    products: Product[];
    onProductSelected: EventEmitter<Product>;

    private currentProduct?: Product;

    constructor() {
        this.products = [];
        this.onProductSelected = new EventEmitter<Product>();
    }

    ngOnInit(): void {
    }

}
