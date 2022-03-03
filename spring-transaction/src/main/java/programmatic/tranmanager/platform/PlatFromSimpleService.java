package programmatic.tranmanager.platform;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @author PengFuLin
 * @version 1.0
 * @date 2022/3/3 21:46
 */
public class PlatFromSimpleService {

    private  PlatformTransactionManager txManager;

    public void someMethod(){
        //设置事务属性：默认事务定义对象
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("SomeTxName"); //事务属性
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED); //事务传播行为
        //获取事务状态对象
        TransactionStatus status = txManager.getTransaction(def);
        try {
            updateOperation1();
        } catch (Exception ex) {
            txManager.rollback(status);  //回滚事务
            throw ex;
        }
        txManager.commit(status);  //提交事务
    }
    private void updateOperation1(){

    }
}



