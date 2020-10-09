package com.kyuhyeon.blockchain;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CkhApplication {

	public static void main(String[] args) {
		SpringApplication.run(CkhApplication.class, args);
		
		FabricConnection conn = new FabricConnection();
		try {
			conn.create();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
