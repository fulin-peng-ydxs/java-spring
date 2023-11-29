package elasticsearch.repositories;


import elasticsearch.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * 商品存储库
 * author: pengshuaifeng
 * 2023/11/29
 */
@Repository
public interface ProductRepository extends ElasticsearchRepository<Product,Long> {
}
