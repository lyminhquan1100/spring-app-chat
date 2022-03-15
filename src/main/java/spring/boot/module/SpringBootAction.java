package spring.boot.module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"spring.boot.core","spring.boot.module.auth","spring.boot.module.chat"})
public class SpringBootAction {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootAction.class,args);
  }
}
