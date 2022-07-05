import odt.client.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "odt.client.api")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class ActionTestMain  {

    public static void main(String[] args) {
        if(ODTComponentFactory.SWING_MODE) {
            Context.NOTEST = false;
            new ODTClient();
        }else {
            Context.NOTEST = true;
            SpringApplication.run(ActionTestMain.class, args);
        }
    }


}
