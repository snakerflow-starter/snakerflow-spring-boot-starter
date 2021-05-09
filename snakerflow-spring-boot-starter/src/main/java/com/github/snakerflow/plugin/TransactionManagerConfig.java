package com.github.snakerflow.plugin;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.*;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhao.guoqing
 * @date 2020/11/25 8:50
 */

@Order
@Configuration
@ConditionalOnClass({ DataSource.class, EmbeddedDatabaseType.class })
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@EnableTransactionManagement
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class TransactionManagerConfig {

    private static final String AOP_POINTCUT_EXPRESSION = "execution(* org.snaker.engine.core..*.*(..))";

    @Bean
    public TransactionInterceptor txAdvice(TransactionManager transactionManager) {
        /*事务管理规则，声明具备事务管理的方法名**/
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        /*只读事务，不做更新操作*/
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        /* transactiondefinition 定义事务的隔离级别 PROPAGATION_NOT_SUPPORTED事务传播级别5，以非事务运行，如果当前存在事务，则把当前事务挂起*/
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        /*当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务*/
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        /*抛出异常后执行切点回滚**/
        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        /*PROPAGATION_REQUIRED:事务隔离性为1，若当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。这是默认值。 */
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        /*设置事务失效时间，如果超过5秒，则回滚事务*/
        requiredTx.setTimeout(5);
        Map<String, TransactionAttribute> txMap = new HashMap<>();

        txMap.put("start*", requiredTx);
        txMap.put("execute*", requiredTx);
        txMap.put("save*", requiredTx);
        txMap.put("delete*", requiredTx);
        txMap.put("update*", requiredTx);
        txMap.put("remove*", requiredTx);
        txMap.put("assign*", requiredTx);
        txMap.put("create*", requiredTx);
        txMap.put("complete*", requiredTx);
        txMap.put("finish*", requiredTx);
        txMap.put("terminate*", requiredTx);
        txMap.put("do*", requiredTx);
        txMap.put("take*", requiredTx);
        txMap.put("deploy*", requiredTx);
        txMap.put("undeploy*", requiredTx);
        txMap.put("redeploy*", requiredTx);
        /*select,count开头的方法,开启只读,提高数据库访问性能*/
        txMap.put("get*", readOnlyTx);
        txMap.put("find*", readOnlyTx);
        txMap.put("query*", readOnlyTx);
        txMap.put("search*", readOnlyTx);
        txMap.put("is*", readOnlyTx);
        txMap.put("*", requiredTx);

        source.setNameMap(txMap);
       return new TransactionInterceptor(transactionManager, source);
    }

    @Bean
    public Advisor txAdviceAdvisor(TransactionInterceptor txAdvice) {
        /* 声明切点的面：切面就是通知和切入点的结合。通知和切入点共同定义了关于切面的全部内容——它的功能、在何时和何地完成其功能* */
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        /*声明和设置需要拦截的方法,用切点语言描写**/
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        /*设置切面=切点pointcut+通知TxAdvice**/
        return new DefaultPointcutAdvisor(pointcut, txAdvice);
    }
}
