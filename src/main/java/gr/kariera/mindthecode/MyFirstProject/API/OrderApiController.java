package gr.kariera.mindthecode.MyFirstProject.API;

import gr.kariera.mindthecode.MyFirstProject.DTOs.NewOrderDto;
import gr.kariera.mindthecode.MyFirstProject.Entities.Order;
import gr.kariera.mindthecode.MyFirstProject.Entities.OrderProduct;
import gr.kariera.mindthecode.MyFirstProject.Entities.OrderProductPK;
import gr.kariera.mindthecode.MyFirstProject.Entities.Product;
import gr.kariera.mindthecode.MyFirstProject.Repositories.OrderRepository;
import gr.kariera.mindthecode.MyFirstProject.Repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping(path = "/api")
public class OrderApiController {

    private final OrderRepository repo;
    private final ProductRepository productRepo;

    public OrderApiController(OrderRepository repo, ProductRepository productRepo) {

        this.repo = repo;
        this.productRepo = productRepo;

    }

    @PutMapping("/orders/{id}")
    public Order update(@PathVariable Integer id, @RequestBody Order order) {

        if (!id.equals(order.getId())) {

            throw new HttpClientErrorException(HttpStatusCode.valueOf(400), "id in path does not patch id in body");

        }

        return repo.save(order);

    }

    @PostMapping("/orders")
    @Transactional
    public Order newPerson(@RequestBody NewOrderDto newOrder) {

        Order order = new Order();
        order.setAddress(newOrder.getAddress());
        order.setDiscountPercentage(newOrder.getDiscountPercentage());
        order = repo.save(order);

        final Order finalOrder = order;
        newOrder.getProducts()
                .stream()
                .forEach(nop -> {

            Product p = productRepo
                    .findById(nop.getProductId())
                    .orElseThrow();
            OrderProduct op = new OrderProduct();
            OrderProductPK opPK = new OrderProductPK();
            opPK.setOrderId(finalOrder.getId());
            opPK.setProductId(p.getId());
            op.setId(opPK);
            op.setOrder(finalOrder);
            op.setProduct(p);
            op.setQuantity(nop.getQuantity());
            finalOrder.getOrderProducts().add(op);
            finalOrder.setOrderProducts(finalOrder.getOrderProducts());

        });

       Order result = repo.save(finalOrder);
       return repo.findById(result.getId())
               .orElseThrow();
       //org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Cannot invoke \"gr.kariera.mindthecode.MyFirstProject.Entities.Product.getId()\" because the return value of \"gr.kariera.mindthecode.MyFirstProject.Entities.OrderProduct.getProduct()\" is null\r\n\tat org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.writeInternal(AbstractJackson2HttpMessageConverter.java:492)\r\n\tat org.springframework.http.converter.AbstractGenericHttpMessageConverter.write(AbstractGenericHttpMessageConverter.java:103)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor.writeWithMessageConverters(AbstractMessageConverterMethodProcessor.java:297)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor.handleReturnValue(RequestResponseBodyMethodProcessor.java:194)\r\n\tat org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite.handleReturnValue(HandlerMethodReturnValueHandlerComposite.java:78)\r\n\tat org.springframework.hateoas.server.mvc.RepresentationModelProcessorHandlerMethodReturnValueHandler.handleReturnValue(RepresentationModelProcessorHandlerMethodReturnValueHandler.java:108)\r\n\tat org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite.handleReturnValue(HandlerMethodReturnValueHandlerComposite.java:78)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:135)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:884)\r\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:797)\r\n\tat org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\r\n\tat org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1080)\r\n\tat org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:973)\r\n\tat org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1011)\r\n\tat org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:914)\r\n\tat jakarta.servlet.http.HttpServlet.service(HttpServlet.java:731)\r\n\tat org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:885)\r\n\tat jakarta.servlet.http.HttpServlet.service(HttpServlet.java:814)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:223)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:158)\r\n\tat org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:185)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:158)\r\n\tat org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:185)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:158)\r\n\tat org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:185)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:158)\r\n\tat org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:185)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:158)\r\n\tat org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:177)\r\n\tat org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:97)\r\n\tat org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:542)\r\n\tat org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:119)\r\n\tat org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)\r\n\tat org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:78)\r\n\tat org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:357)\r\n\tat org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:400)\r\n\tat org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:65)\r\n\tat org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:859)\r\n\tat org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1734)\r\n\tat org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52)\r\n\tat org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1191)\r\n\tat org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659)\r\n\tat org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\r\n\tat java.base/java.lang.Thread.run(Thread.java:833)\r\nCaused by: com.fasterxml.jackson.databind.JsonMappingException: Cannot invoke \"gr.kariera.mindthecode.MyFirstProject.Entities.Product.getId()\" because the return value of \"gr.kariera.mindthecode.MyFirstProject.Entities.OrderProduct.getProduct()\" is null (through reference chain: gr.kariera.mindthecode.MyFirstProject.Entities.Order[\"products\"])\r\n\tat com.fasterxml.jackson.databind.JsonMappingException.wrapWithPath(JsonMappingException.java:402)\r\n\tat com.fasterxml.jackson.databind.JsonMappingException.wrapWithPath(JsonMappingException.java:361)\r\n\tat com.fasterxml.jackson.databind.ser.std.StdSerializer.wrapAndThrow(StdSerializer.java:316)\r\n\tat com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:782)\r\n\tat com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)\r\n\tat com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)\r\n\tat com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)\r\n\tat com.fasterxml.jackson.databind.ObjectWriter$Prefetch.serialize(ObjectWriter.java:1572)\r\n\tat com.fasterxml.jackson.databind.ObjectWriter.writeValue(ObjectWriter.java:1061)\r\n\tat org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter.writeInternal(AbstractJackson2HttpMessageConverter.java:483)\r\n\t... 50 more\r\nCaused by: java.lang.NullPointerException: Cannot invoke \"gr.kariera.mindthecode.MyFirstProject.Entities.Product.getId()\" because the return value of \"gr.kariera.mindthecode.MyFirstProject.Entities.OrderProduct.getProduct()\" is null\r\n\tat gr.kariera.mindthecode.MyFirstProject.Entities.Order.lambda$getProducts$0(Order.java:72)\r\n\tat java.base/java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197)\r\n\tat java.base/java.util.Iterator.forEachRemaining(Iterator.java:133)\r\n\tat java.base/java.util.Spliterators$IteratorSpliterator.forEachRemaining(Spliterators.java:1845)\r\n\tat java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)\r\n\tat java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)\r\n\tat java.base/java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:921)\r\n\tat java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)\r\n\tat java.base/java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:682)\r\n\tat gr.kariera.mindthecode.MyFirstProject.Entities.Order.getProducts(Order.java:76)\r\n\tat java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n\tat java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)\r\n\tat java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n\tat java.base/java.lang.reflect.Method.invoke(Method.java:568)\r\n\tat com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:689)\r\n\tat com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)\r\n\t... 56 more\r\n

    }

    @GetMapping("/orders/{id}")
    public Order one(@PathVariable Integer id) {

        return repo.findById(id)
                .orElseThrow();

    }

    @GetMapping("/orders")
    public Page<Order> all(

            @RequestParam(required = false) String address,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "ASC", required = false) String sort

    ) {

        PageRequest paging = PageRequest
                .of(page, size)
                .withSort(sort.equalsIgnoreCase("ASC") ?
                        Sort.by("address").ascending() :
                        Sort.by("address").descending());

        Page<Order> res;

        if (address == null) {

            res = repo.findAll(paging);

        }

        else {
            res = repo.findByAddressContainingIgnoreCase(address, paging);

        }

        return res;

    }

    @DeleteMapping("/orders/{id}")
    public void delete(@PathVariable Integer id) {

        Order match = repo.findById(id)
                .orElseThrow();
        repo.delete(match);

    }

}