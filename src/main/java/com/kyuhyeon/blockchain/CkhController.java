package com.kyuhyeon.blockchain;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CkhController {
	@RequestMapping(value = "/test")
	public String test(){
        return "TEST";
	}
}
