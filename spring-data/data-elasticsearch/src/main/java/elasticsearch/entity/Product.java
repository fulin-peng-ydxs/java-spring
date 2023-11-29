package elasticsearch.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 商品映射
 * author: pengshuaifeng
 * 2023/11/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
//文档注释：索引名称（没有默认会由"引导存储存储库"对象自动创建）、主分片=3 、副本为1
@Document(indexName = "shopping", shards = 3, replicas = 1)
public class Product {

    //文档标识，等同于文档的"_id"属性
    @Id
    private Long id;

    //文档字段，文档的数据属性
    /**
     * type : 字段数据类型
     * analyzer : 分词器类型
     * */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;//商品名称
    @Field(type = FieldType.Keyword)
    private String category;//分类名称
    @Field(type = FieldType.Double)
    private Double price;//商品价格
    /**
     * type :  Keyword : 短语,不进行分词
     * index : 是否索引(默认:true)
     * */
    @Field(type = FieldType.Keyword, index = false)
    private String images;//图片地址
}
