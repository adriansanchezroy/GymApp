package datacenter;

public abstract class User {
	private String name;
	private String email;
	private String phoneNumber;
	private String address;
	private String province;
	private String postalCode;
	private String id;
	private String city;
	private static int lastKey = 0;
	private boolean deleted;

	protected User(String name, String email, String phoneNumber, String address, String province, String city,
			String postalCode) {
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.province = province;
		this.postalCode = postalCode;
		this.city = city;
		this.lastKey += 1;
		this.id = String.format("%09d", (this.lastKey));
		this.deleted = false;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getId() {
		return this.id;
	}

	public String getEmail() {
		return this.email;
	}

	public String getProvince() {
		return this.province;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return  this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void deleteUser() {
		this.deleted = true;
	}

	public boolean isDeleted() {
		return this.deleted;
	}
}
