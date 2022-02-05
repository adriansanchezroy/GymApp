package datacenter;

import java.util.ArrayList;
import java.time.*;

public class Session {
	private LocalDate date;
	private Service service;
	private ArrayList<Registration> registrations;
	private String id;
	private String code;
	private static int lastKey = 0;

	public Session(LocalDate date, Service service) {
		this.date = date;
		this.service = service;
		registrations = new ArrayList<Registration>();
		this.lastKey += 1;
		this.id = String.format("%02d", (this.lastKey));
		this.code = this.service.getId() + this.id + this.service.getProfessional().getId().substring(7, 9);
	}


	/**
	 * Ajoute une inscription a la seance.
	 * 
	 * @param r L'inscription a ajouter.
	 */
	public void addRegistration(Registration r) {
		this.registrations.add(r);
		System.out.println("Inscription créée pour " + r.getMember().getName()
				+ ", veuillez confirmer l'inscription à nouveau avant d'assister à la séance.");
	}



	/**
	 * Trouve l'inscription d'un membre a cette séance
	 * 
	 * @param mbr Membre duquel on cherche l'inscription a cette seance
	 * @return les inscriptions du membre
	 */
	public Registration getRegistrationForMember(Member mbr) {
		for (Registration r : registrations) {
			if (r.getMember().equals(mbr)) {
				return r;
			}
		}
		return null;
	}

	/**
	 * Imprime une liste des inscriptions a la seance.
	 */
	public void listRegistrations() {
		for (Registration r : registrations) {
			System.out.println(r.getMember().getName());
		}
	}


	/**
	 * Imprime une liste des inscriptions des membres participant aux seances
	 * donnees par un professionnel.
	 */
	public void listRegistrationsForProfessionnal(Professional pro) {
		System.out.println("Inscription pour le service: " + this.code);
		for (Registration r : registrations) {
			if (r.getProfessional().equals(pro)) {
				System.out.println(r.getMember().getName());
			}
		}
	}

	public ArrayList<Registration> getRegistrations() {
		return registrations;
	}

	
	public String getId() {
		return this.id;
	}
	public LocalDate getDate() {
		return this.date;
	}

	public String getCode() {
		return this.code;
	}

	public Service getService() {
		return this.service;
	}
}
