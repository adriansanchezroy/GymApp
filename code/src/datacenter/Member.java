package datacenter;

public class Member extends User {
	private boolean suspended;

	public Member(String name, String email, String phoneNumber, String address, String province, String city,
			String postalCode) {
		super(name, email, phoneNumber, address, province, city, postalCode);
		this.suspended = false;
	}

	/**
	 * Permet de savoir si le membre est suspendu ou non.
	 * 
	 * @return Si le membre est suspendu.
	 */
	public boolean isSuspended() {
		return this.suspended;
	}

}
