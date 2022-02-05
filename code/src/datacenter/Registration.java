package datacenter;

import java.io.*;
import java.time.*;


public class Registration {
	private LocalDateTime registrationDate;
	private Member member;
	private Professional professional;
	private Session session;
	private String comments;
	private boolean confirmed;

	public Registration(LocalDateTime registrationDate, Member member, Professional professional, Session session,
			String comments) {
		this.registrationDate = registrationDate;
		this.member = member;
		this.professional = professional;
		this.session = session;
		this.comments = comments;
		this.confirmed = false;
	}

	/**
	 * Confirme l'inscription et ecrit les informations necessaires dans un nouveau
	 * fichier .reg.
	 */
	public void confirmation() {
		String filename = this.member.getName() + this.member.getId() + session.getCode() + ".reg";
		try {
			
			String message = LocalDateTime.now().toString() + "\n" + this.session.getDate().toString() + "\n"
					+ this.professional.getId() + "\n" + this.member.getId() + "\n" + this.session.getCode() + "\n"
					+ this.comments;
			DataCenter.saveFiles("confirmations", filename, message);
			this.confirmed = true;
			System.out.println("L'inscription est confirmée");

		} catch (IOException e) {
			System.out.println("An error occurred.");
		}
	}

	public Member getMember() {
		return this.member;
	}

	public Professional getProfessional() {
		return this.professional;
	}

	public boolean isConfirmed() {
		return this.confirmed;
	}

	public Session getSession() {
		return this.session;
	}

	public LocalDateTime getRegistrationDate() {
		return this.registrationDate;
	}

}
