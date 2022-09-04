package edu.lysak.springjms.config;

import com.thoughtworks.xstream.security.AnyTypePermission;
import edu.lysak.springjms.domain.Book;
import edu.lysak.springjms.domain.BookOrder;
import edu.lysak.springjms.domain.Customer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.oxm.xstream.XStreamMarshaller;

@EnableJms
@Configuration
public class JmsConfig {

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
    public ActiveMQConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(
                "admin",
                "admin",
                "tcp://localhost:61616"
        );
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
}
