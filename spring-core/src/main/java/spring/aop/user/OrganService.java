package spring.aop.user;

import org.springframework.stereotype.Service;
import spring.aop.annotation.ProxyTarget;

@Service
@ProxyTarget
public class OrganService {

    public String getOrgan(){
        System.out.println("À¤À¤ÑøÖ³»ùµØ");
        return "À¤À¤";
    }
}
