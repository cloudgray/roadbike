package com.kyuhyeon.blockchain;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DemoController {
	@RequestMapping(value = "/test")
	public String test(){
        return "TEST";
	}
	
	@RequestMapping(value = "/admin")
	public String enrollAdmin(){
        FabricCA ca = new FabricCA();
        try {
			ca.enrollAdmin();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "SUCCESS";
	}
	
	@RequestMapping(value = "/user")
	public String registerUser(){
		FabricCA ca = new FabricCA();
		try {
			ca.registerUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "SUCCESS";
	}
	
	// Parts Company produces parts
	@RequestMapping(value = "/produce/transmission")
	public String produceTransmission(){
		FabricConnection conn = new FabricConnection();
		try {
			conn.shimano.producePart("transmission");
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "SUCCESS";
	}
	
	@RequestMapping(value = "/produce/brake")
	public String produceBrake(){
		FabricConnection conn = new FabricConnection();
		try {
			conn.shimano.producePart("brake");
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "SUCCESS";
	}
	
	@RequestMapping(value = "/produce/wheel")
	public String produceWheel(){
		FabricConnection conn = new FabricConnection();
		try {
			conn.shimano.producePart("wheel");
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "SUCCESS";
	}
	
	// Manufacturer purchases parts
	@RequestMapping(value = "/purchase/transmission")
	public String purchaseTransmission(){
		FabricConnection conn = new FabricConnection();
		try {
			conn.specialized.purchasePart("transmission");
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "SUCCESS";
	}
	
	@RequestMapping(value = "/purchase/brake")
	public String purchaseBrake(){
		FabricConnection conn = new FabricConnection();
		try {
			conn.specialized.purchasePart("brake");
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "SUCCESS";
	}
	
	@RequestMapping(value = "/purchase/wheel")
	public String purchaseWheel(){
		FabricConnection conn = new FabricConnection();
		try {
			conn.specialized.purchasePart("wheel");
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "SUCCESS";
	}
	
	@RequestMapping(value = "/produce/roadbike")
	public String produceRoadbike(){
		FabricConnection conn = new FabricConnection();
		try {
			conn.specialized.produceRoadbike();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "SUCCESS";
	}
	
	@RequestMapping(value = "/purchase/roadbike")
	public String purchaseRoadbike(){
		FabricConnection conn = new FabricConnection();
		try {
			conn.realBikeShop.purchaseRoadbike();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "SUCCESS";
	}
	
	@RequestMapping(value = "/bikelist")
	public String getBikeList(){
		FabricConnection conn = new FabricConnection();
		try {
			conn.realBikeShop.getBikeList();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "SUCCESS";
	}
	
	

	
	@RequestMapping(value = "/create")
	public String create(){
		FabricConnection conn = new FabricConnection();
		try {
			conn.create();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "SUCCESS";
	}
	
	@RequestMapping(value = "/aaa")
	public String produceTransmission2(){
		FabricConnections2 conn = new FabricConnections2();
		try {
			conn.aaa();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "SUCCESS";
	}

	@RequestMapping(value = "/enroll")
	public String enroll(){
		EnrollAdmin ea = new EnrollAdmin();
		try {
			ea.enroll();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "SUCCESS";
	}

	@RequestMapping(value = "/register")
	public String register(){
		RegisterUser ru = new RegisterUser();
		try {
			ru.register();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "SUCCESS";
	}
}
