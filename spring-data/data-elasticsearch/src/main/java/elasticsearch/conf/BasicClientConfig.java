package elasticsearch.conf;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
//使用存储库功能：操作文档和自动维护索引
@EnableElasticsearchRepositories(value = "elasticsearch.repositories")
public class BasicClientConfig extends AbstractElasticsearchConfiguration {

	/**
	 * rest高级java客户端对象：spring-data一般不直接操作此对象，而是作为提供底层支持，
	 * 由data-存储库对象或es-操作/模版对象来访问
	 * 2023/11/29 22:12
	 * @author pengshuaifeng
	 */

	@Override
	public RestHighLevelClient elasticsearchClient() {
		RestClientBuilder builder = RestClient.builder(new HttpHost("192.168.1.106",9200));
		return new RestHighLevelClient(builder);
	}
}