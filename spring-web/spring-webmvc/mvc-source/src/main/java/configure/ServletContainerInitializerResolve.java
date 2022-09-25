package configure;

/**
 * @author PengFuLin
 * @version 1.0
 * @description: SpringMvc代码配置原理
 * @date 2021/11/21 21:40
 */
public class ServletContainerInitializerResolve {
/**
 * 1> 基于Servlet3.0新特性：
 * web容器在启动的时候，会扫描每个jar包下的加载这个文件指定的类SpringServletContainerInitializer
 *
 * 2> spring实现方案：
 * spring-web的ETA-INF/services/javax.servlet.ServletContainerInitializer中
 * 指定了实现ServletContainerInitializer的SpringServletContainerInitializer类
 */

////1.会加载感兴趣的WebApplicationInitializer接口的下的所有组件；
//@HandlesTypes({WebApplicationInitializer.class})
//public class SpringServletContainerInitializer implements ServletContainerInitializer {
//    public SpringServletContainerInitializer() {
//    }
//    //Servlet容器启动时会调用onStartup()方法：
//    public void onStartup(@Nullable Set<Class<?>> webAppInitializerClasses, ServletContext servletContext) throws ServletException {
//        /**
//         * 2、为WebApplicationInitializer组件创建对象（组件不是接口，不是抽象类）
//         *     ==》 这里通过实现类完成对Web组件和SpringIOC容器对组件配置
//         *
//         * 	1）、AbstractContextLoaderInitializer：
//         * 	      #创建根容器；createRootApplicationContext()；
//         *
//         * 	2）、AbstractDispatcherServletInitializer：
//         * 	       ==》继承AbstractContextLoaderInitializer
//         * 		  #创建一个web的IOC容器；createServletApplicationContext();
//         * 		  #创建了DispatcherServlet；createDispatcherServlet()；
//         * 		  #将创建的DispatcherServlet添加到ServletContext中；
//         * 		  #完成对其他web组件的注册到ServletContext中
//         *
//         * 	3）、AbstractAnnotationConfigDispatcherServletInitializer：
//         * 		  #实现对根容器的创建：createRootApplicationContext()
//         * 	     	getRootConfigClasses();传入根节点配置类
//         * 	      #实现对web的ioc容器的创建： createServletApplicationContext();
//         * 	 		getServletConfigClasses();传入webIOC配置类
//         */
//        List<WebApplicationInitializer> initializers = new LinkedList();
//        Iterator var4;
//        if (webAppInitializerClasses != null) {
//            var4 = webAppInitializerClasses.iterator();
//            while(var4.hasNext()) {
//                Class<?> waiClass = (Class)var4.next();
//                if (!waiClass.isInterface() && !Modifier.isAbstract(waiClass.getModifiers())
//                        && WebApplicationInitializer.class.isAssignableFrom(waiClass)) {
//                    try {
//                        initializers.add((WebApplicationInitializer) ReflectionUtils.accessibleConstructor(waiClass, new Class[0]).newInstance());
//                    } catch (Throwable var7) {
//                        throw new ServletException("Failed to instantiate WebApplicationInitializer class", var7);
//                    }
//                }
//            }
//        }
//        if (initializers.isEmpty()) {
//            servletContext.log("No Spring WebApplicationInitializer types detected on classpath");
//        } else {
//            /**
//             * 调用创建好的WebApplicationInitializer组件的onStartup完成SpringMVC容器的创建工作！
//             */
//            servletContext.log(initializers.size() + " Spring WebApplicationInitializers detected on classpath");
//            AnnotationAwareOrderComparator.sort(initializers);
//            var4 = initializers.iterator();
//            while(var4.hasNext()) {
//                WebApplicationInitializer initializer = (WebApplicationInitializer)var4.next();
//                initializer.onStartup(servletContext);
//            }
//
//        }
//    }
//}
}
