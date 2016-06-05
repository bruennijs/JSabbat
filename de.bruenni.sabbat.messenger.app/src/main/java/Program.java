/**
 * Created by bruenni on 27.05.16.
 */

package sabbat.messenger.app;

import infrastructure.common.event.IDomainEventBus;
import infrastructure.common.event.IEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sabbat.messenger.core.application.services.MessageApplicationService;

public class Program {

    /**
     * MUst be called lower cadse
     * @param args
     */
    public static void main(String[] args) {
        /*new ClassPathResource().*/
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring/spring-all.xml"});
        IDomainEventBus<IEvent> domainEventBus = (IDomainEventBus<IEvent>) context.getBean("DomainEventBus");
        MessageApplicationService messageApplicationService = (MessageApplicationService)context.getBean("MessageApplicationService");
        messageApplicationService.send("hello", "content txt");
        System.out.println("HEllo messenger app");
    }
}
