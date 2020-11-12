package com.kyuhyeon.blockchain;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;

public class RoadbikeCCService {
	private Path walletPath;
	private Wallet wallet;
	private Path networkConfigPath;
	private Gateway.Builder builder;
	private Gateway gateway;
	private Network network;
	private Contract contract;
	
	public PartsCompany shimano;
	public Manufacturer specialized;
	public BikeStore realBikeShop;
	public Consumer consumer;
	

	public RoadbikeCCService(String userId) {
		super();
		try {
			// Load a file system based wallet for managing identities.
			walletPath = Paths.get("wallet");
			wallet = Wallet.createFileSystemWallet(walletPath);
			
			// load a CCP
			networkConfigPath = Paths.get("fabric-samples", "first-network", "connection-org1.yaml");
			
			builder = Gateway.createBuilder();
			builder.identity(wallet,userId).networkConfig(networkConfigPath).discovery(true);
//			builder.identity(wallet, "admin").networkConfig(networkConfigPath).discovery(true);
			
			// create a gateway connection
			gateway = builder.connect();
			
			// get the network and contract
			network = gateway.getNetwork("mychannel");
			contract = network.getContract("mycc");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		shimano = new PartsCompany();
		specialized = new Manufacturer();
		realBikeShop = new BikeStore();
		consumer = new Consumer();
		
	}
	
	public String getCrankset(String key) {
		String rtn = "";
		try {
			byte[] result = contract.evaluateTransaction("GetCrankset", key);
			rtn = new String(result);
			System.out.println(rtn);
		} catch (ContractException e) {
			e.printStackTrace();
		}
		return rtn;
	}
	
	public String getRoadbike(String key) {
		String rtn = "";
		try {
			byte[] result = contract.evaluateTransaction("GetRoadbike", key);
			rtn = new String(result);
			System.out.println(new String(result));
		} catch (ContractException e) {
			e.printStackTrace();
		}
		return rtn;
	}
	
	public class PartsCompany {
		PartsCompany() {
			super();
		}
		
		public String getShimano() {
			String rtn = "";
			try {
				byte[] result = contract.submitTransaction("GetShimano");
				rtn = new String(result);
				System.out.println(new String(result));
			} catch (ContractException | TimeoutException | InterruptedException e) {
				e.printStackTrace();
			} 
			return rtn;
		}
		
		public String getCranksetList() {
			String rtn = "";
			try {
				byte[] result = contract.submitTransaction("GetCranksetList");
				rtn = new String(result);
				System.out.println(new String(result));
			} catch (ContractException | TimeoutException | InterruptedException e) {
				e.printStackTrace();
			} 
			return rtn;
		}
		
		
		public String produceCrankset() {
			String rtn = "";
			try {
				byte[] result = contract.submitTransaction("ProduceCrankset");
				rtn = new String(result);
				System.out.println(new String(result));
			} catch (ContractException | TimeoutException | InterruptedException e) {
				e.printStackTrace();
			} 
			return rtn;
		}
		
	}
	
	
	public class Manufacturer {
		Manufacturer() {
			super();
		}
		
		public String getSpecialized() {
			String rtn = "";
			try {
				byte[] result = contract.submitTransaction("GetSpecialized");
				rtn = new String(result);
				System.out.println(new String(result));
			} catch (ContractException | TimeoutException | InterruptedException e) {
				e.printStackTrace();
			} 
			return rtn;
		}
		
		public String purchaseCrankset() {
			String rtn = "";
			try {
				byte[] result = contract.submitTransaction("PurchaseCrankset");
				rtn = new String(result);
				System.out.println(new String(result));
			} catch (ContractException | TimeoutException | InterruptedException e) {
				e.printStackTrace();
			} 
			return rtn;
		}
		
		public String getRoadbikeList() {
			String rtn = "";
			try {
				byte[] result = contract.submitTransaction("GetRoadbikeList");
				rtn = new String(result);
				System.out.println(new String(result));
			} catch (ContractException | TimeoutException | InterruptedException e) {
				e.printStackTrace();
			} 
			return rtn;
		}
		
		public String produceRoadbike() {
			String rtn = "";
			try {
				byte[] result = contract.submitTransaction("ProduceRoadbike");
				rtn =  new String(result);
				System.out.println(new String(result));
			} catch (ContractException | TimeoutException | InterruptedException e) {
				e.printStackTrace();
			} 
			return rtn;
		}
	}
	
	
	public class BikeStore {
		public String getRealBikeShop() {
			String rtn = "";
			try {
				byte[] result = contract.submitTransaction("GetRealBikeShop");
				rtn = new String(result);
				System.out.println(new String(result));
			} catch (ContractException | TimeoutException | InterruptedException e) {
				e.printStackTrace();
			} 
			return rtn;
		}
		
		public String warehouseRoadbike() {
			String rtn = "";
			try {
				byte[] result = contract.submitTransaction("WarehouseRoadbike");
				rtn = new String(result);
				System.out.println(new String(result));
			} catch (ContractException | TimeoutException | InterruptedException e) {
				e.printStackTrace();
			} 
			return rtn;
		}
		
		public String getBikeList() {
			String rtn = "";
			try {
				byte[] result = contract.evaluateTransaction("GetBikeList");
				rtn = new String(result);
				System.out.println(new String(result));
			} catch (ContractException e) {
				e.printStackTrace();
			}
			return rtn;
		}
	}
	
	
	public class Consumer {
		Consumer() {
			super();
		}
		
		public String purchaseRoadbike(String owner, String sn) {	
			String rtn = "";
			try {
				byte[] result = contract.submitTransaction("PurchaseRoadbike", owner, sn);
				rtn = new String(result);
				System.out.println(new String(result));
			} catch (ContractException | TimeoutException | InterruptedException e) {
				e.printStackTrace();
			}
			return rtn;
		}
		
		public String getMyBikeInfo(String owner) {
			String rtn = "";
			try {
				byte[] result = contract.evaluateTransaction("GetMyBikeInfo", owner);
				rtn = new String(result);
				System.out.println(new String(result));
			} catch (ContractException e) {
				e.printStackTrace();
			}
			return rtn;
		}
		
		public String makeTrade(String owner, String sn, int price) {
			String rtn = "";
			String paramPrice = String.valueOf(price);
			try {
				byte[] result = contract.submitTransaction("MakeTrade", owner, sn, paramPrice);
				rtn = new String(result);
				System.out.println(rtn);
			} catch (ContractException | TimeoutException | InterruptedException e) {
				e.printStackTrace();
			}
			return rtn;
		}
		
		public String signTrade(String tradeId, String userId) {
			String rtn = "";
			try {
				byte[] result = contract.submitTransaction("SignTrade", tradeId, userId);
				rtn = new String(result);
				System.out.println(rtn);
			} catch (ContractException | TimeoutException | InterruptedException e) {
				e.printStackTrace();
			}
			return rtn;
		}
		
		public String getActiveTradeList() {
			String rtn = "";
			try {
				byte[] result = contract.submitTransaction("GetActiveTradeList");
				rtn = new String(result);
				System.out.println(new String(result));
			} catch (ContractException | TimeoutException | InterruptedException e) {
				e.printStackTrace();
			} 
			return rtn;
		}
	}

}
