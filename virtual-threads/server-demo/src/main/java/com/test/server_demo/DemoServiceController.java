package com.test.server_demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoServiceController {

	@GetMapping("demo-service")
	public void demoService() throws InterruptedException {
		
		Thread.sleep(3000); // 3 segundos
		
	}
	
}
