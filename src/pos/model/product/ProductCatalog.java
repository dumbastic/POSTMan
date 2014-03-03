package pos.model.product;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by dumbastic on 22/02/14.
 * Singleton class to generate product catalog
 */
public class ProductCatalog {
    private Map<String, ProductSpecification> catalog = new HashMap<String, ProductSpecification>();

    private static ProductCatalog instance = new ProductCatalog();

    public static ProductCatalog getInstance() {
        if (instance == null)
            instance = new ProductCatalog();
        return instance;
    }

    private ProductCatalog() {
        this.catalog = new ProductCatalogGenerator().getAllProducts();
    }

    public Map<String, ProductSpecification> getCatalog() {
        return this.catalog;
    }

    /**
     * get product specification based on product ID
     * @param id
     * @return
     */
    public ProductSpecification getProductSpecification( String id ) {
        return catalog.get(id);
    }
}
