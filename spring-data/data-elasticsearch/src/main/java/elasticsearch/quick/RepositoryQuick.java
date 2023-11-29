package elasticsearch.quick;

import elasticsearch.conf.BasicClientConfig;
import elasticsearch.entity.Product;
import elasticsearch.repositories.ProductRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;

/**
 * 存储库操作案例
 * author: pengshuaifeng
 * 2023/11/29
 */
public class RepositoryQuick {


    public static AnnotationConfigApplicationContext getApplication(){
        return new AnnotationConfigApplicationContext(BasicClientConfig.class);
    }


    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = getApplication();
        ProductRepository productRepository = context.getBean(ProductRepository.class);
        ElasticsearchOperations operations = context.getBean(ElasticsearchOperations.class);
        //创建索引
        createIndex();
        //删除索引
//        deleteIndex(operations);
        //文档curd
        curdDocument(productRepository);
    }

    /**
     * 创建索引
     * 2023/11/29 00:48
     * @author pengshuaifeng
     */
    public static void createIndex(){
        //存储库初始化会自动创建和更新索引
        System.out.println("创建索引");
    }

    /**
     * 删除索引
     * 2023/11/29 00:45
     * @author pengshuaifeng
     */
    public static void deleteIndex(ElasticsearchOperations operations){
        IndexOperations indexOperations = operations.indexOps(Product.class);
        indexOperations.delete();
        System.out.println("删除索引");
    }

    /**
     * 文档curd
     * 2023/11/29 00:45
     * @author pengshuaifeng
     */
    public static void curdDocument(ProductRepository productRepository){
        //新增文档
        Product product = new Product();
        product.setId(1L);
        product.setPrice(22.0);
        product.setCategory("饮料");
        product.setImages("/root/images/1.jpg");
        product.setTitle("高级饮料");
        productRepository.save(product);
        System.out.println("新增文档。。。。。。。");
        //查询文档
        System.out.println("查询文档。。。。。。。");
        productRepository.findAll().forEach(System.out::println);
        //更新文档
        product.setTitle("高级的中国饮料");
        productRepository.save(product);
        System.out.println("更新文档。。。。。。。");
        System.out.println("查询文档。。。。。。。");
        productRepository.findAll().forEach(System.out::println);
        //删除文档
        productRepository.delete(product);
        System.out.println("删除文档。。。。。。。。");
    }
}
