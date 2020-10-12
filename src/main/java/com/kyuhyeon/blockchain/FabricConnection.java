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

public class FabricConnection {
	private Path walletPath;
	private Wallet wallet;
	private Path networkConfigPath;
	private Gateway.Builder builder;
	private Gateway gateway;
	private Network network;
	private Contract contract;
	
	public PartsCompany shimano;
	public Manufacturer specialized;
	public BikeStore bikeStore;
	public Consumer consumer;
	

	public FabricConnection() {
		super();
		try {
			// Load a file system based wallet for managing identities.
			walletPath = Paths.get("wallet");
			wallet = Wallet.createFileSystemWallet(walletPath);
			// load a CCP
			networkConfigPath = Paths.get("fabric-samples", "test-network", "organizations", "peerOrganizations", "org1.example.com", "connection-org1.yaml");

			builder = Gateway.createBuilder();
			builder.identity(wallet, "appUser").networkConfig(networkConfigPath).discovery(true);
			
			// create a gateway connection
			gateway = builder.connect();
			
			// get the network and contract
			network = gateway.getNetwork("mychannel");
			contract = network.getContract("roadbike-scm");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public class PartsCompany {
		PartsCompany() {
			super();
		}
		
		public void producePart(String partName) {
			try {
				contract.submitTransaction("producePart", partName);
			} catch (ContractException | TimeoutException | InterruptedException e) {
				e.printStackTrace();
			} 
		}
		
		
	}
	
	public class Manufacturer {
		Manufacturer() {
			super();
		}
		
		public void purchasePart(String partName) {
			try {
				contract.submitTransaction("producePart", partName);
			} catch (ContractException | TimeoutException | InterruptedException e) {
				e.printStackTrace();
			} 
		}
		
		public void produceRoadbike() {
			try {
				contract.submitTransaction("produceRoadbike");
			} catch (ContractException | TimeoutException | InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}
	
	public class BikeStore {
		public void purchaseRoadbike() {
			try {
				contract.submitTransaction("purchaseRoadbike");
			} catch (ContractException | TimeoutException | InterruptedException e) {
				e.printStackTrace();
			} 
		}
		
		public void getBikeList() {
			try {
				contract.evaluateTransaction("getBikeList");
			} catch (ContractException e) {
				e.printStackTrace();
			}
		}
	}
	
	public class Consumer {
		Consumer() {
			super();
		}
		
		public void purchaseMyBike(String owner, String sn) {		
			try {
				contract.submitTransaction("purchaseMyBike", owner, sn);
			} catch (ContractException | TimeoutException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public String getMyBikeInfo(String owner) {
			byte[] result = null;
			try {
				result = contract.submitTransaction("getMyBikeInfo", owner);
			} catch (ContractException | TimeoutException | InterruptedException e) {
				e.printStackTrace();
			}
			return result.toString();
		}
		
		public void changeOwner(String owner1, String owner2) {
			try {
				contract.submitTransaction("changeOwner", owner1, owner2);
			} catch (ContractException | TimeoutException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	

	public void create() throws Exception {
		// Load a file system based wallet for managing identities.
		Path walletPath = Paths.get("wallet");
		Wallet wallet = Wallet.createFileSystemWallet(walletPath);
		// load a CCP
		Path networkConfigPath = Paths.get("fabric-samples", "test-network", "organizations", "peerOrganizations", "org1.example.com", "connection-org1.yaml");

		Gateway.Builder builder = Gateway.createBuilder();
		builder.identity(wallet, "appUser").networkConfig(networkConfigPath).discovery(true);

		// create a gateway connection
		try (Gateway gateway = builder.connect()) {

			// get the network and contract
			Network network = gateway.getNetwork("mychannel");
			Contract contract = network.getContract("roadbike-scm");

//			byte[] result;
//
//			result = contract.evaluateTransaction("queryAllCars");
//			System.out.println(new String(result));

			contract.submitTransaction("Produce", "transmission");

//			result = contract.evaluateTransaction("queryCar", "CAR10");
//			System.out.println(new String(result));
//
//			contract.submitTransaction("changeCarOwner", "CAR10", "Archie");
//
//			result = contract.evaluateTransaction("queryCar", "CAR10");
//			System.out.println(new String(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}
}
