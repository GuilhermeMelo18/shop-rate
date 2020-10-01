import { ProductRate } from './product-rate';

export class Product {
    id: string;
    name: string;
    price: string;
    productRateList: Array<ProductRate>;

    constructor() {
        this.productRateList = new Array<ProductRate>();
    }
}
