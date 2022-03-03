package programmatic.transaction.template;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class TemplateSimpleService {

    private final TransactionTemplate transactionTemplate;


    public TemplateSimpleService(PlatformTransactionManager transactionManager) {
        //注意：需要注入事务管理器
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    //操作方法有返回的情况
    public Object someCallbackServiceMethod() {
        /**
         * 流程：调用template中的execute方法，将要管理的事务方法放入自定义的TransactionCallback实现类中的doInTransaction，即可完成spring对事务的控制。
         * 回调中的代码可以通过调用：TransactionStatus对象提供的方法：setRollbackOnly()来回滚事务。
         */
        return transactionTemplate.execute(new TransactionCallback<Object>() {
            public Object doInTransaction(TransactionStatus status) {
                try {
                    updateOperation1();  //操作方法1
                    return resultOfUpdateOperation2(); //操作方法2
                }catch (Exception e) {
                    status.setRollbackOnly();
                    return null;
                }
            }
        });
    }

    //操作方法无返回值的情况
    public void someWithoutResultServiceMethod(){
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    updateOperation1();
                    updateOperation2();
                }catch (Exception e) {
                    status.setRollbackOnly();
                }
            }
        });
    }

    private void updateOperation1(){

    }

    private void updateOperation2(){
    }

    private Object resultOfUpdateOperation2(){
        return null;
    }
}