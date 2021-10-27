package com.learncamel.route;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.learncamel.bean.MyBean;

@Component
public class SimpleCamelRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        
    	from("timer:hello?period=10s")
    		.to("direct:start");
		
    	  from("direct:start")
          .log("Main route: Send '${body}' to tap router")
          .wireTap("direct:tap")
          .log("Main route: Add 'two' to '${body}'")
          .bean(MyBean.class, "addTwo")
          .log("Main route: Output '${body}'");

          from("direct:tap")
          .log("Tap Wire route: received '${body}'")
          .log("Tap Wire route: Add 'three' to '${body}'")
          .bean(MyBean.class, "addThree")
          .log("Tap Wire route: Output '${body}'");
    }
}
