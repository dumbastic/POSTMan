package pos.model.product;

import java.math.BigDecimal;

/**
 * Created by dumbastic on 22/02/14.
 * this class store the product specification for each product which contains id, name, and price
 */
public class ProductSpecification {
    private String id;
    private String name;
    private BigDecimal price;

    public ProductSpecification ( String id, String name, BigDecimal price ) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
