# dubbo 演示实例

搭建集群需要考虑的问题

## dubbo 解决什么问题

1. 怎么去维护url
  通过注册中心去维护url（ Zookeeper，redis，memcache）
2. F5硬件负载均衡的单电压力比较大
    软负载均衡
3. 怎么去整理出服务之间的依赖关系
    自动去整理各个服务之间的依赖
4. 如果服务器的调用量越来越大，服务器的容量问题如何去评估，扩容指标？
    需要一个监控平台，可以监控调用量，响应时间等等...
 
## dubbo是什么

Dubbo 是分布式的服务框架，主要提供高性能的以及透明化的RPC远程服务调用解决方案，以及SOA服务治理方案。
Dubbo的核心部分：     
    远程通信
    集群容错
    服务的自动发现
    负载均衡
 
## Dubbo的架构

核心角色
    Provider  服务提供者
    Consumer  服务消费者
    Registry     注册中心
    Monitor     监控中心
    Container   web容器
 
Smiple 监控中心

Monitor 也是一个dubbo服务，所以需要配置 zookeeper 和port

 
### dubbo 注册中心 协议

1. multicast  组播的注册方式  注意mac需要在启动的时候加上 -Djava.net.preferIPv4Stack=true 防止ipv6 报出异常
2. zookeeper 注册中心， 官方推荐
3. redis 注册中心  由于官方不支持使用密码注册 暂时不推荐
4. simple注册中心，可以当做一个monitor 。
 
### dubbo的配置

如果提供方没有启动的时候，默认去检测所依赖的服务是否正常提供服务
如果check为false，表示启动的时候不检查。
dubbo:reference  check 默认是true，当出现循环依赖的时候要把check属性设置为false
dubbo:consumer check=false  没有提供者的时候报错
dubbo:register check=false  注册失败的时候报错

### dubbo 通信协议配置

dubbo 缺省协议
连接个数：单连接
连接方式：长连接
传输协议：TCP
传输方式：NIO 异步传输
序列化：Hessian 二进制序列化
适用范围：传入传出参数数据包较小（建议小于100K），消费者比提供者个数多，单一消费者无法压满提供者，尽量不要用 dubbo 协议传输大文件或超大字符串。
适用场景：常规远程服务方法调用
hessian协议
连接个数：多连接
连接方式：短连接
传输协议：HTTP
传输方式：同步传输
序列化：Hessian二进制序列化
适用范围：传入传出参数数据包较大，提供者比消费者个数多，提供者压力较大，可传文件。
适用场景：页面传输，文件传输，或与原生hessian服务互操作
Dubbo 版本控制

    <dubbo:service interface="com.weidai.data.api.IOrderService" ref="orderService" protocol="hessian" version="1.0"/> 
加上provider端 加上版本号，不同的实现加上不同的版本，对应的consumer 也必须加上版本号，否则会报 找不到提供者的异常。

### 连接超时设置，

必须呀设置服务处理的超时时间
<dubbo:service timeout="5000">

### 负载均衡算法设置

<dubbo:service timeout="5000" loadBalance >
### 集群容错

Failover cluster （默认）：失败的时候自动切换 并重试其他服务器。通过retries=num 来射中重试次数。默认等于2。
failfast cluster ：快速失败，只发起一次调用；比如写操作，或者非幂等的请求。
failsafe cluster: 失败安全。出现异常时，直接忽略异常。
failback cluster：失败自动恢复，后台记录失败请求，定时重发
forking cluster :并行调用多个服务器，只要有一个成功就返回。只能应用在读请求
broadcast cluster：广播调用所有提供者，逐个调用。其中一台报错就返回异常。
### 配置在消费者
    <dubbo:reference  cluster="failfast"/>

### 配置的优先级

reference method > service  method > reference >service > consumer > provider

### 服务的最佳实践

分包
1. 服务接口，请求服务模型，异常信息都放在api里面，符合重用发布等价原则，共同重用原则。
2. api 里面放入spring的引用配置。也可以放在模块的包目录下。
<import resource="classpath*:/META-INF/client/order-costumer.xml">
粒度
1.尽可能把接口设置成粗粒度，每个服务方法代表一个独立的功能，而不是某个功能的步骤，否则可能会涉及到分布式事务。
2. 服务接口建议以业务场景为单位划分。并对相近业务做抽象，防止接口暴增。
3. 不建议使用过于抽象的通用接口，接口没有明确的语义，带来后期的维护。

版本
1. 每个接口都应该定义版本，为后续的兼容性提供前瞻性的考虑。
2. 建议使用两位版本号，因为第三位版本号表示的兼容性升级，只有不兼容时才需要变更服务版本
3. 当接口做到不兼容升级的时候，先升级一半或者一台提供者为新版本，再讲消费者全部升级新版本，再将剩下一般提供者升级新版本。（预发布环境）

### 推荐用法

在provider端尽可能配置consumer端的属性。比如：timeout，retires，线程池大小，loadBalance

配置管理员信息
application 上配置的owner 建议配置2个人以上。owner在监控中心都能看到

配置dubbo缓存文件
<dubbo:registry  file="d://dubbo.cache"> 当注册中心不可用的时候，会将 服务提供者的url缓存下来。
注册中心的列表和服务提供者列表。

### 源码分析

NamespaceHandler 注册BeanDefinitionParser 利用它来解析
BeanDefinitionParser 解析配置文件的元素

spring会默认加载jar 包下 /META-INF/spring.handlers 找到对应的NamespaceHandler

initializingBean 当spring容器初始化完成后，会调用afterPropertiesSet方法
DisposableBean  实现 dostroy 方法a
ApplicationContextAware 
ApplicationListener
BeanNameAware
