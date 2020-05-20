package compatator;

import java.util.Comparator;

import tmall.Product;

public class ProductPriceComparator implements Comparator<Product> {

      public int compare(Product o1, Product o2) {
    	  return (int)(o1.getPromotePrice()-o2.getPromotePrice());
    	  
      }
}
