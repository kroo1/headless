import odt.context.Context;
import odt.context.ODTComponentFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "odt.api")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class ActionTestMain  {

    public static void main(String[] args) {
        if(ODTComponentFactory.SWING_MODE) {
            Context.NOTEST = false;
            ODTComponentFactory.getContext();
            SpringApplication.run(ActionTestMain.class, args);
        }else {
            Context.NOTEST = true;
            ODTComponentFactory.getContext();
            SpringApplication.run(ActionTestMain.class, args);
        }
    }


}
