package pos.model.product;

import pos.model.product.ProductSpecification;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by dumbastic on 23/02/14.
 * this class will generate product catalog from csv file
 */
public class ProductCatalogGenerator {

    /**
     * this method return list of all products retrieved from ProductCatalog.csv
     * @return
     */
    public HashMap<String, ProductSpecification> getAllProducts() {

        HashMap<String, ProductSpecification> productMap = new HashMap<String, ProductSpecification>();
        File catalogFile = new File("ProductCatalog.csv");

        try {
            Scanner scanner = new Scanner(catalogFile);

            while (scanner.hasNext()) {
                String fileRecord = scanner.nextLine();
                String[] fileRecordSplit = fileRecord.split(",");

                ProductSpecification product =
                        new ProductSpecification(fileRecordSplit[0],
                                fileRecordSplit[1],
                                new BigDecimal(fileRecordSplit[2]));

                productMap.put(product.getID(), product);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return productMap;
    }
}
