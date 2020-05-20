package compatator;

import java.util.Comparator;

import tmall.Product;

public class ProductReviewComparartor implements Comparator<Product> {
        public int compare(Product o1, Product o2) {
        	return o2.getReviewCount()-o1.getReviewCount();
        }
}
