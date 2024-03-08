package programmatic.tranmanager.platform;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 事务管理器
 * 手动实现事务管理
 * @author pengshuaifeng
 * 2024/3/8
 */
@Service
public class TransactionManager {

    //TheadLocal 保存每个线程的事务状态
    private static final ThreadLocal<TransactionStatus> threadLocal = new ThreadLocal<>();

    // 事务管理器
    @Autowired
    private PlatformTransactionManager transactionManager;

    /**
     * 开始事务 事务传播行为：PROPAGATION_REQUIRED
     */
    public TransactionStatus begin(DefaultTransactionDefinition definition) {
        TransactionStatus transaction = transactionManager.getTransaction(definition);
        threadLocal.set(transaction);
        return transaction;
    }

    /**
     * 开始事务 事务传播行为：PROPAGATION_REQUIRES_NEW
     */
    public TransactionStatus begin() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return begin(def);
    }

    /**
     * 提交事务
     */
    public void commit() {
        TransactionStatus status = threadLocal.get();
        if (status != null) {
            try {
                transactionManager.commit(status);
            }finally {
                endTransaction();
            }
        }
    }

    /**
     * 回滚事务
     */
    public void rollback() {
        TransactionStatus status = threadLocal.get();
        if (status != null) {
            try {
                transactionManager.rollback(status);
            } finally {
                endTransaction();
            }
        }
    }

    private void endTransaction() {
        threadLocal.remove();
    }
}
