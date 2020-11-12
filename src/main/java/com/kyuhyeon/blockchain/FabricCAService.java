package com.kyuhyeon.blockchain;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.Properties;
import java.util.Set;

import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallet.Identity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import org.hyperledger.fabric_ca.sdk.EnrollmentRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;



public class FabricCAService {
	private Properties props;
	private Wallet wallet;
	private HFCAClient caClient;
	
	// Create a CA client for interacting with the CA.
	public FabricCAService(String port) {
		super();
		String url = "https://localhost:" + port;
		props = new Properties();
		props.put("pemFile",
				"fabric-samples/first-network/crypto-config/peerOrganizations/org1.example.com/ca/ca.org1.example.com-cert.pem");
		props.put("allowAllHostNames", "true");
	
		try {
			caClient = HFCAClient.createNewInstance(url, props);
			CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
			caClient.setCryptoSuite(cryptoSuite);
			
			// Create a wallet for managing identities
			wallet = Wallet.createFileSystemWallet(Paths.get("wallet"));
		} catch (MalformedURLException | InvalidArgumentException | CryptoException | InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	
	public String enrollAdmin() throws Exception{
		String rtn = "";
		
		// Check to see if we've already enrolled the admin user.
		boolean adminExists = wallet.exists("admin");
        if (adminExists) {
        	rtn = "An identity for the admin user \"admin\" already exists in the wallet";
            System.out.println(rtn);
            return rtn;
        }

        // Enroll the admin user, and import the new identity into the wallet.
        final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
        enrollmentRequestTLS.addHost("localhost");
        enrollmentRequestTLS.setProfile("tls");
        Enrollment enrollment = caClient.enroll("admin", "adminpw", enrollmentRequestTLS);
        Identity user = Identity.createIdentity("Org1MSP", enrollment.getCert(), enrollment.getKey());
        wallet.put("admin", user);
        rtn = "Successfully enrolled user \"admin\" and imported it into the wallet";
		System.out.println(rtn);
		return rtn;
	}
	
	public String enrollOrg2Admin() throws Exception{
		String rtn = "";
		
		// Check to see if we've already enrolled the admin user.
		boolean adminExists = wallet.exists("org2Admin");
        if (adminExists) {
        	rtn = "An identity for the admin user \"admin\" already exists in the wallet";
            System.out.println(rtn);
            return rtn;
        }

        // Enroll the admin user, and import the new identity into the wallet.
        final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
        enrollmentRequestTLS.addHost("localhost");
        enrollmentRequestTLS.setProfile("tls");
        Enrollment enrollment = caClient.enroll("org2Admin", "adminpw", enrollmentRequestTLS);
        Identity user = Identity.createIdentity("Org2MSP", enrollment.getCert(), enrollment.getKey());
        wallet.put("org2Admin", user);
        rtn = "Successfully enrolled user \"org2Admin\" and imported it into the wallet";
		System.out.println(rtn);
		return rtn;
	}
	
	public String enrollOrg3Admin() throws Exception{
		String rtn = "";
		
		// Check to see if we've already enrolled the admin user.
		boolean adminExists = wallet.exists("org3Admin");
        if (adminExists) {
        	rtn = "An identity for the admin user \"admin\" already exists in the wallet";
            System.out.println(rtn);
            return rtn;
        }

        // Enroll the admin user, and import the new identity into the wallet.
        final EnrollmentRequest enrollmentRequestTLS = new EnrollmentRequest();
        enrollmentRequestTLS.addHost("localhost");
        enrollmentRequestTLS.setProfile("tls");
        Enrollment enrollment = caClient.enroll("org3Admin", "adminpw", enrollmentRequestTLS);
        Identity user = Identity.createIdentity("Org3MSP", enrollment.getCert(), enrollment.getKey());
        wallet.put("org3Admin", user);
        rtn = "Successfully enrolled user \"org3Admin\" and imported it into the wallet";
		System.out.println(rtn);
		return rtn;
	}
	
	
	public String registerUser(String userId) throws Exception {
		String rtn = "";
		
		// Check to see if we've already enrolled the user.
		boolean userExists = wallet.exists(userId);
		if (userExists) {
			rtn = "An identity for the user \"" + userId + "\" already exists in the wallet";
			System.out.println(rtn);
			return rtn;
		}

		userExists = wallet.exists("admin");
		if (!userExists) {
			rtn ="\"admin\" needs to be enrolled and added to the wallet first";
			System.out.println(rtn);
			return rtn;
		}

		Identity adminIdentity = wallet.get("admin");
		User admin = new User() {

			
			@Override
			public String getName() {
				return "admin";
			}

			@Override
			public Set<String> getRoles() {
				return null;
			}

			@Override
			public String getAccount() {
				return null;
			}

			@Override
			public String getAffiliation() {
				return "org1.department1";
			}

			@Override
			public Enrollment getEnrollment() {
				return new Enrollment() {

					@Override
					public PrivateKey getKey() {
						return adminIdentity.getPrivateKey();
					}

					@Override
					public String getCert() {
						return adminIdentity.getCertificate();
					}
				};
			}

			@Override
			public String getMspId() {
				return "Org1MSP";
			}

		};

		// Register the user, enroll the user, and import the new identity into the wallet.
		RegistrationRequest registrationRequest = new RegistrationRequest(userId);
		registrationRequest.setAffiliation("org1.department1");
		registrationRequest.setEnrollmentID(userId);
		String enrollmentSecret = caClient.register(registrationRequest, admin);
		Enrollment enrollment = caClient.enroll(userId, enrollmentSecret);
		Identity user = Identity.createIdentity("Org1MSP", enrollment.getCert(), enrollment.getKey());
		wallet.put(userId, user);
		rtn = "Successfully enrolled user \""+ userId +"\" and imported it into the wallet";
		System.out.println(rtn);
		return rtn;
	}
	
	public String registerOrg2User(String userId) throws Exception {
		String rtn = "";
		
		// Check to see if we've already enrolled the user.
		boolean userExists = wallet.exists(userId);
		if (userExists) {
			rtn = "An identity for the user \"" + userId + "\" already exists in the wallet";
			System.out.println(rtn);
			return rtn;
		}

		userExists = wallet.exists("org2Admin");
		if (!userExists) {
			rtn ="\"org2Admin\" needs to be enrolled and added to the wallet first";
			System.out.println(rtn);
			return rtn;
		}

		Identity adminIdentity = wallet.get("org2Admin");
		User admin = new User() {

			
			@Override
			public String getName() {
				return "org2Admin";
			}

			@Override
			public Set<String> getRoles() {
				return null;
			}

			@Override
			public String getAccount() {
				return null;
			}

			@Override
			public String getAffiliation() {
				return "org2.department1";
			}

			@Override
			public Enrollment getEnrollment() {
				return new Enrollment() {

					@Override
					public PrivateKey getKey() {
						return adminIdentity.getPrivateKey();
					}

					@Override
					public String getCert() {
						return adminIdentity.getCertificate();
					}
				};
			}

			@Override
			public String getMspId() {
				return "Org2MSP";
			}

		};

		// Register the user, enroll the user, and import the new identity into the wallet.
		RegistrationRequest registrationRequest = new RegistrationRequest(userId);
		registrationRequest.setAffiliation("org2.department1");
		registrationRequest.setEnrollmentID(userId);
		String enrollmentSecret = caClient.register(registrationRequest, admin);
		Enrollment enrollment = caClient.enroll(userId, enrollmentSecret);
		Identity user = Identity.createIdentity("Org2MSP", enrollment.getCert(), enrollment.getKey());
		wallet.put(userId, user);
		rtn = "Successfully enrolled user \""+ userId +"\" and imported it into the wallet";
		System.out.println(rtn);
		return rtn;
	}
	
	public String registerOrg3User(String userId) throws Exception {
		String rtn = "";
		
		// Check to see if we've already enrolled the user.
		boolean userExists = wallet.exists(userId);
		if (userExists) {
			rtn = "An identity for the user \"" + userId + "\" already exists in the wallet";
			System.out.println(rtn);
			return rtn;
		}

		userExists = wallet.exists("org3Admin");
		if (!userExists) {
			rtn ="\"org3Admin\" needs to be enrolled and added to the wallet first";
			System.out.println(rtn);
			return rtn;
		}

		Identity adminIdentity = wallet.get("org3Admin");
		User admin = new User() {

			
			@Override
			public String getName() {
				return "org3Admin";
			}

			@Override
			public Set<String> getRoles() {
				return null;
			}

			@Override
			public String getAccount() {
				return null;
			}

			@Override
			public String getAffiliation() {
				return "org3.department1";
			}

			@Override
			public Enrollment getEnrollment() {
				return new Enrollment() {

					@Override
					public PrivateKey getKey() {
						return adminIdentity.getPrivateKey();
					}

					@Override
					public String getCert() {
						return adminIdentity.getCertificate();
					}
				};
			}

			@Override
			public String getMspId() {
				return "Org3MSP";
			}

		};

		// Register the user, enroll the user, and import the new identity into the wallet.
		RegistrationRequest registrationRequest = new RegistrationRequest(userId);
		registrationRequest.setAffiliation("org3.department1");
		registrationRequest.setEnrollmentID(userId);
		String enrollmentSecret = caClient.register(registrationRequest, admin);
		Enrollment enrollment = caClient.enroll(userId, enrollmentSecret);
		Identity user = Identity.createIdentity("Org3MSP", enrollment.getCert(), enrollment.getKey());
		wallet.put(userId, user);
		rtn = "Successfully enrolled user \""+ userId +"\" and imported it into the wallet";
		System.out.println(rtn);
		return rtn;
	}
}
