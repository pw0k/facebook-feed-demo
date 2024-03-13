package pw.feed.postwriter.config;

import java.lang.reflect.Method;

import javax.sql.DataSource;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;

/**
* for logging SQL query, uncomment block if u need
 * example output
 * 2024-03-10T21:44:15.636+03:00  INFO 81918 --- [   scheduling-1] n.t.d.l.l.SLF4JQueryLoggingListener      : Name:, Connection:41, Time:1, Success:True, Type:Prepared, Batch:False, QuerySize:1, BatchSize:0, Query:["select nextval('post_id_seq')"], Params:[()]
 * 2024-03-10T21:44:15.636+03:00  INFO 81918 --- [   scheduling-1] n.t.d.l.l.SLF4JQueryLoggingListener      : Name:, Connection:41, Time:1, Success:True, Type:Prepared, Batch:False, QuerySize:1, BatchSize:0, Query:["select nextval('post_id_seq')"], Params:[()]
 * 2024-03-10T21:44:15.639+03:00  INFO 81918 --- [   scheduling-1] n.t.d.l.l.SLF4JQueryLoggingListener      : Name:, Connection:41, Time:3, Success:True, Type:Prepared, Batch:False, QuerySize:1, BatchSize:0, Query:["select nextval('post_id_seq')"], Params:[()]
 * 2024-03-10T21:44:15.640+03:00  INFO 81918 --- [   scheduling-1] n.t.d.l.l.SLF4JQueryLoggingListener      : Name:, Connection:41, Time:1, Success:True, Type:Prepared, Batch:False, QuerySize:1, BatchSize:0, Query:["select nextval('post_outbox_id_seq')"], Params:[()]
 * 2024-03-10T21:44:15.642+03:00  INFO 81918 --- [   scheduling-1] n.t.d.l.l.SLF4JQueryLoggingListener      : Name:, Connection:41, Time:1, Success:True, Type:Prepared, Batch:False, QuerySize:1, BatchSize:0, Query:["select nextval('post_outbox_id_seq')"], Params:[()]
 * 2024-03-10T21:44:15.642+03:00  WARN 81918 --- [   scheduling-1] pw.feed.postwriter.service.PostService   : inserted for 0sec
 * 2024-03-10T21:44:15.647+03:00  INFO 81918 --- [   scheduling-1] n.t.d.l.l.SLF4JQueryLoggingListener      : Name:, Connection:41, Time:3, Success:True, Type:Prepared, Batch:True, QuerySize:1, BatchSize:22, Query:["insert into post (created_at,description,group_id,title,user_id,id)
 * values (?,?,?,?,?,?)"], Params:[(2024-03-10 21:44:15.642794,Look at me!,NULL(INTEGER), I'm Mr. Meeseeks 78255633,15,1304361),(2024-03-10 21:44:15.642963,Look at me!,NULL(INTEGER), I'm Mr. Meeseeks 78255633,15,1304362),(2024-03-10 21:44:15.64302,Look at me!,NULL(INTEGER),
 * I'm Mr. Meeseeks 78255633,15,1304363),(2024-03-10 21:44:15.64307,Look at me!,NULL(INTEGER), I'm Mr. Meeseeks 78255633,15,1304364),(2024-03-10 21:44:15.643117,Look at me!,NULL(INTEGER), I'm Mr. Meeseeks 78255633,15,1304365),(2024-03-10 21:44:15.643162,Look at me!,NULL(INTEGER),
 * I'm Mr. Meeseeks 78255633,15,1304366),(2024-03-10 21:44:15.643208,Look at me!,NULL(INTEGER), I'm Mr. Meeseeks 78255633,15,1304367),(2024-03-10 21:44:15.643253,Look at me!,NULL(INTEGER), I'm Mr. Meeseeks 78255633,15,1304368),(2024-03-10 21:44:15.643298,Look at me!,NULL(INTEGER),
 * I'm Mr. Meeseeks 78255633,15,1304369),(2024-03-10 21:44:15.643346,Look at me!,NULL(INTEGER), I'm Mr. Meeseeks 78255633,15,1304370),(2024-03-10 21:44:15.643391,Look at me!,NULL(INTEGER), I'm Mr. Meeseeks 78255633,15,1304371),(2024-03-10 21:44:15.643436,Look at me!,NULL(INTEGER),
 * I'm Mr. Meeseeks 78255633,15,1304372),(2024-03-10 21:44:15.643509,Look at me!,NULL(INTEGER), I'm Mr. Meeseeks 78255633,15,1304373),(2024-03-10 21:44:15.643556,Look at me!,NULL(INTEGER), I'm Mr. Meeseeks 78255633,15,1304374),(2024-03-10 21:44:15.643613,Look at me!,NULL(INTEGER),
 * I'm Mr. Meeseeks 78255633,15,1304375),(2024-03-10 21:44:15.643659,Look at me!,NULL(INTEGER), I'm Mr. Meeseeks 78255633,15,1304376),(2024-03-10 21:44:15.643705,Look at me!,NULL(INTEGER), I'm Mr. Meeseeks 78255633,15,1304377),(2024-03-10 21:44:15.643757,Look at me!,NULL(INTEGER),
 * I'm Mr. Meeseeks 78255633,15,1304378),(2024-03-10 21:44:15.643812,Look at me!,NULL(INTEGER), I'm Mr. Meeseeks 78255633,15,1304379),(2024-03-10 21:44:15.643845,Look at me!,NULL(INTEGER), I'm Mr. Meeseeks 78255633,15,1304380),(2024-03-10 21:44:15.643925,Look at me!,NULL(INTEGER),
 * I'm Mr. Meeseeks 78255633,15,1304381),(2024-03-10 21:44:15.643971,Look at me!,NULL(INTEGER), I'm Mr. Meeseeks 78255633,15,1304382)]
*/
@Component
public class DatasourceProxyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
//        if (bean instanceof DataSource) {
//            ProxyFactory factory = new ProxyFactory(bean);
//            factory.setProxyTargetClass(true);
//            factory.addAdvice(new ProxyDataSourceInterceptor((DataSource) bean));
//            return factory.getProxy();
//        }
        return bean;
    }

    private record ProxyDataSourceInterceptor(DataSource dataSource) implements MethodInterceptor {
            private ProxyDataSourceInterceptor(final DataSource dataSource) {
                this.dataSource = ProxyDataSourceBuilder.create(dataSource).countQuery().logQueryBySlf4j(SLF4JLogLevel.INFO).build();
            }

            @Override
            public Object invoke(final MethodInvocation invocation) throws Throwable {
                Method proxyMethod = ReflectionUtils.findMethod(dataSource.getClass(), invocation.getMethod().getName());
                if (proxyMethod != null) {
                    return proxyMethod.invoke(dataSource, invocation.getArguments());
                }
                return invocation.proceed();
            }
        }
}