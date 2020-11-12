package com.kyuhyeon.blockchain;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DemoController {
	@RequestMapping(value = "/test")
	public String test(){
        return "TEST";
	}
	
	@RequestMapping(value = "/admin")
	public String enrollAdmin(@RequestParam(value = "org") String org){
		String rtn = "";
		String port = "";
		if (org.equals("shimano")) {
			port = "7054";
		} else if (org.equals("specialized")) {
			port = "8054";
		} else {
			port = "9054";
		}
        FabricCAService ca = new FabricCAService(port);
        try {
        	if (org.equals("shimano")) {
        		rtn = ca.enrollAdmin();
    		} else if (org.equals("specialized")) {
    			rtn = ca.enrollOrg2Admin();
    		} else {
    			rtn = ca.enrollOrg3Admin();
    		}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        return rtn;
	}
	
	@RequestMapping(value = "/user")
	public String registerUser(@RequestParam(value = "org") String org,
			@RequestParam(value = "userid") String userId){
		String rtn = "";
		String port = "";
		if (org.equals("shimano")) {
			port = "7054";
		} else if (org.equals("specialized")) {
			port = "8054";
		} else {
			port = "9054";
		}
		FabricCAService ca = new FabricCAService(port);
		try {
			if (org.equals("shimano")) {
				rtn = ca.registerUser(userId);
    		} else if (org.equals("specialized")) {
    			rtn = ca.registerOrg2User(userId);
    		} else {
    			rtn = ca.registerOrg3User(userId);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return rtn;
	}
	
	
	@RequestMapping(value = "/crankset", method = { RequestMethod.GET })
	public String getCrankset(@RequestParam(value = "sn") String sn,
			@RequestParam(value = "userid") String userId){
		String rtn = "";
		RoadbikeCCService roadbikeCCService = new RoadbikeCCService(userId);
		try {
			rtn = roadbikeCCService.getCrankset(sn);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return rtn;
	}
	
	@RequestMapping(value = "/roadbike", method = { RequestMethod.GET })
	public String getRoadbike(@RequestParam(value = "userid") String userId,
			@RequestParam(value = "sn") String sn){
		String rtn = "";
		RoadbikeCCService roadbikeCCService = new RoadbikeCCService(userId);
		try {
			rtn = roadbikeCCService.getRoadbike(sn);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return rtn;
	}
	
	
	@RequestMapping(value = "/shimano/crankset", method = { RequestMethod.GET })
	public String produceCrankset(@RequestParam(value = "userid") String userId){
		String rtn = "";
		RoadbikeCCService roadbikeCCService = new RoadbikeCCService(userId);
		try {
			rtn = roadbikeCCService.shimano.produceCrankset();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return rtn;
	}
	
	@RequestMapping(value = "/specialized/crankset", method = { RequestMethod.GET })
	public String purchaseCrankset(@RequestParam(value = "userid") String userId){
		String rtn = "";
		RoadbikeCCService roadbikeCCService = new RoadbikeCCService(userId);
		try {
			rtn = roadbikeCCService.specialized.purchaseCrankset();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return rtn;
	}
	
	@RequestMapping(value = "/specialized/roadbike", method = {RequestMethod.GET})
	public String produceRoadbike(@RequestParam(value = "userid") String userId){
		String rtn = "";
		RoadbikeCCService roadbikeCCService = new RoadbikeCCService(userId);
		try {
			rtn = roadbikeCCService.specialized.produceRoadbike();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return rtn;
	}
	
	@RequestMapping(value = "/realbikeshop/roadbike", method = {RequestMethod.GET})
	public String warehouseRoadbike(@RequestParam(value = "userid") String userId){
		String rtn = "";
		RoadbikeCCService roadbikeCCService = new RoadbikeCCService(userId);
		try {
			rtn = roadbikeCCService.realBikeShop.warehouseRoadbike();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return rtn;
	}
	
	@RequestMapping(value = "/realbikeshop/bikelist", method = {RequestMethod.GET})
	public String getBikeList(@RequestParam(value = "userid") String userId){
		String rtn = "";
		RoadbikeCCService roadbikeCCService = new RoadbikeCCService(userId);
		try {
			rtn = roadbikeCCService.realBikeShop.getBikeList();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return rtn;
	}

	
	@RequestMapping(value = "/consumer/roadbike", method = {RequestMethod.GET})
	public String purchaseRoadbike(@RequestParam(value = "userid") String userId,
			@RequestParam(value = "sn") String sn){
		String rtn = "";
		RoadbikeCCService roadbikeCCService = new RoadbikeCCService(userId);
		try {
			rtn = roadbikeCCService.consumer.purchaseRoadbike(userId, sn);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return rtn;
	}

	@RequestMapping(value = "/consumer/myroadbike", method = {RequestMethod.GET})
	public String getMyBikeInfo(@RequestParam(value = "userid") String userId){
		String rtn = "";
		RoadbikeCCService roadbikeCCService = new RoadbikeCCService(userId);
		try {
			roadbikeCCService.consumer.getMyBikeInfo(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return rtn;
	}
	
	@RequestMapping(value = "/consumer/trade", method = {RequestMethod.GET})
	public String makeTrade(@RequestParam(value = "userid") String userId,
			@RequestParam(value = "sn") String sn,
			@RequestParam(value = "price") int price){
		String rtn = "";
		RoadbikeCCService roadbikeCCService = new RoadbikeCCService(userId);
		try {
			rtn = roadbikeCCService.consumer.makeTrade(userId, sn, price);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return rtn;
	}
	
	@RequestMapping(value = "/consumer/sign", method = {RequestMethod.GET})
	public String signTrade(@RequestParam(value = "userid") String userId,
			@RequestParam(value = "tradeid") String tradeId){
		String rtn = "";
		RoadbikeCCService roadbikeCCService = new RoadbikeCCService(userId);
		try {
			rtn = roadbikeCCService.consumer.signTrade(tradeId, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return rtn;
	}
	
	@RequestMapping(value = "/consumer/tradelist", method = {RequestMethod.GET})
	public String getActiveTradeList(@RequestParam(value = "userid") String userId){
		String rtn = "";
		RoadbikeCCService roadbikeCCService = new RoadbikeCCService(userId);
		try {
			rtn = roadbikeCCService.consumer.getActiveTradeList();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return rtn;
	}
	
	@RequestMapping(value = "/shimano/stock", method = {RequestMethod.GET})
	public String getShimano(@RequestParam(value = "userid") String userId){
		String rtn = "";
		RoadbikeCCService roadbikeCCService = new RoadbikeCCService(userId);
		try {
			rtn = roadbikeCCService.shimano.getShimano();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return rtn;
	}
	
	@RequestMapping(value = "/specialized/stock", method = {RequestMethod.GET})
	public String getSpecialized(@RequestParam(value = "userid") String userId){
		String rtn = "";
		RoadbikeCCService roadbikeCCService = new RoadbikeCCService(userId);
		try {
			rtn = roadbikeCCService.specialized.getSpecialized();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return rtn;
	}

	@RequestMapping(value = "/realbikeshop/stock", method = {RequestMethod.GET})
	public String getRealBikeShop(@RequestParam(value = "userid") String userId){
		String rtn = "";
		RoadbikeCCService roadbikeCCService = new RoadbikeCCService(userId);
		try {
			rtn = roadbikeCCService.realBikeShop.getRealBikeShop();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return rtn;
	}
}
