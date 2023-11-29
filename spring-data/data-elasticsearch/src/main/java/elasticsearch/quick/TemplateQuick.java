package elasticsearch.quick;


import elasticsearch.entity.Product;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;

/**
 * 模版操作案例
 * author: pengshuaifeng
 * 2023/11/29
 */
public class TemplateQuick {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = RepositoryQuick.getApplication();
        ElasticsearchOperations operations = context.getBean(ElasticsearchOperations.class);
        //创建索引
        createIndex(operations);
        //删除索引
//        deleteIndex(operations);
        //文档curd
        curdDocument(operations);
    }

    /**
     * 创建索引
     * 2023/11/29 00:48
     * @author pengshuaifeng
     */
    public static void createIndex(ElasticsearchOperations operations){
        IndexOperations indexOperations = operations.indexOps(Product.class);
        indexOperations.create();
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
    public static void curdDocument(ElasticsearchOperations operations){
        //新增文档
        Product product = new Product();
        product.setId(1L);
        product.setPrice(22.0);
        product.setCategory("饮料");
        product.setImages("/root/images/1.jpg");
        product.setTitle("高级饮料");
        operations.save(product);
        System.out.println("新增文档。。。。。。。");
        //查询文档
        System.out.println("查询文档。。。。。。。");
        System.out.println(operations.get("1", Product.class));
        //更新文档
        product.setTitle("高级的中国饮料");
        operations.save(product);
        System.out.println("更新文档。。。。。。。");
        System.out.println("查询文档。。。。。。。");
        System.out.println(operations.get("1", Product.class));
        //删除文档
        operations.delete("1", Product.class);
        System.out.println("删除文档。。。。。。。。");
    }

}
