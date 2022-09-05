package edu.lysak.springjms.config;

import com.thoughtworks.xstream.security.AnyTypePermission;
import edu.lysak.springjms.domain.Book;
import edu.lysak.springjms.domain.BookOrder;
import edu.lysak.springjms.domain.Customer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.oxm.xstream.XStreamMarshaller;

@EnableJms
@Configuration
public class JmsConfig { //implements JmsListenerConfigurer {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String user;

    @Value("${spring.activemq.password}")
    private String password;


    @Bean
    public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
//        factory.setMessageConverter(jacksonJmsMessageConverter());
        factory.setMessageConverter(xmlMarshallingMessageConverter());
        factory.setConcurrency("1-1");
        return factory;
    }

    @Bean
    public SingleConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
                user,
                password,
                brokerUrl
        );
        SingleConnectionFactory singleConnectionFactory = new SingleConnectionFactory(factory);
        singleConnectionFactory.setReconnectOnException(true);
        singleConnectionFactory.setClientId("myClientId");
        return singleConnectionFactory;
    }

//    @Bean
//    public MessageConverter jacksonJmsMessageConverter() {
//        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//        converter.setTargetType(MessageType.TEXT);
//        converter.setTypeIdPropertyName("_type");
//        return converter;
//    }

    @Bean
    public MessageConverter xmlMarshallingMessageConverter() {
        MarshallingMessageConverter converter = new MarshallingMessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setMarshaller(xmlMarshaller());
        converter.setUnmarshaller(xmlMarshaller());
        return converter;
    }

    @Bean
    public XStreamMarshaller xmlMarshaller(){
        XStreamMarshaller marshaller =  new XStreamMarshaller();
        marshaller.setSupportedClasses(Book.class, Customer.class, BookOrder.class);
        marshaller.setTypePermissions(AnyTypePermission.ANY);
        return marshaller;
    }

//    @Bean
//    public BookOrderProcessingMessageListener jmsMessageListener(){
//        return new BookOrderProcessingMessageListener();
//    }
//
//    @Override
//    public void configureJmsListeners(JmsListenerEndpointRegistrar registrar) {
//        SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
//        endpoint.setMessageListener(jmsMessageListener());
//        endpoint.setDestination("book.order.processed.queue");
//        endpoint.setId("book-order-processed-queue");
//        endpoint.setConcurrency("1");
//        endpoint.setSubscription("my-subscription");
//        registrar.setContainerFactory(defaultJmsListenerContainerFactory());
//        registrar.registerEndpoint(endpoint, defaultJmsListenerContainerFactory());
//    }

}
