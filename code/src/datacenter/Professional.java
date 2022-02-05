package datacenter;

import java.util.ArrayList;

public class Professional extends User {
	private ArrayList<Service> services;

	public Professional(String name, String email, String phoneNumber, String address, String province, String city,
			String postalCode) {
		super(name, email, phoneNumber, address, province, city, postalCode);
		this.services = new ArrayList<>();
	}

	/**
	 * Imprime la liste des services fournis par le professionnel.
	 */
	public void listServices() {
		for (Service service : this.services) {
			service.getServiceInfo();
		}
	}

	public void addService(Service service){
		this.services.add(service);
	}

}
