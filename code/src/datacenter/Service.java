package datacenter;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.time.*;

public class Service {

	private String id;
	private String name;
	private String comments;
	private LocalDate creationDate;
	private LocalDate serviceStartDate;
	private LocalDate serviceEndDate;
	private LocalDate registrationDate;
	private ArrayList<DayOfWeek> recurrence;
	private Professional professional;
	private int maxMembers;
	private String hours;
	private double price;
	private ArrayList<Session> sessions;
	private static int lastKey = 0;

	public Service(LocalDate creationDate, LocalDate serviceStartDate, LocalDate serviceEndDate,
			ArrayList<DayOfWeek> recurrence, String name, Professional professional, String comments, int maxMembers,
			double price,String hours) {
		this.creationDate = creationDate;
		this.serviceStartDate = serviceStartDate;
		this.serviceEndDate = serviceEndDate;
		this.name = name;
		this.professional = professional;
		this.comments = comments;
		this.maxMembers = maxMembers;
		this.price = price;
		this.recurrence = recurrence;
		this.sessions = new ArrayList<>();
		this.hours=hours;
		this.lastKey += 1;
		this.id = String.format("%03d", (this.lastKey));

		addSessions();
	}

	/**
	 * Instancie toutes les seances pour le service.
	 */
	public void addSessions() {
		System.out.println("Generating list of sessions for service " + this.name);
		LocalDate dateIterator = this.serviceStartDate;

		while (dateIterator.isBefore(serviceEndDate) || dateIterator.isEqual(serviceEndDate)) {
			for (DayOfWeek dayOfWeek : this.recurrence) {
				if (dateIterator.getDayOfWeek().equals(dayOfWeek)) {
					Session session = new Session(dateIterator, this);
					System.out.println("Created session #" + session.getCode() + " for " + dateIterator.toString());
					this.sessions.add(session);

				}
			}
			dateIterator = dateIterator.plusDays(1);
		}
	}

	/**
	 * Supprime les séances futures.
	 */
	public void removeFutureSessions() {
		System.out.println("Deleting future sessions for service " + this.name);
		LocalDate today = LocalDate.now();
		
		for (Session session : this.sessions) {
			if (session.getDate().isAfter(today) || session.getDate().isEqual(today)) {
				sessions.remove(session);
			}
		}
	}
	
	/**
	 * Imprime le service, son nom, les informations des seances en date du jour
	 * s'il en existe et un message informant l'utilisateur qu'il n'en existe pas
	 * sinon.
	 */
	public void getServiceInfo() {
		System.out.println("Service " + this.name + " #" + this.id);
		if (this.hasSessionToday()) {
			Session todaySession = this.getTodaysSession();
			System.out.println("Session #" + todaySession.getCode());
			todaySession.listRegistrations();
		} else {
			System.out.println("Ce service n'a pas de séance aujourd'hui");
		}
	}

	/**
	 * Permet de savoir si le service a une seance qui se deroule en date du jour.
	 * 
	 * @return Si oui ou non le service a une seance en date du jour.
	 */
	public boolean hasSessionToday() {
		LocalDate today = LocalDate.now();
		for (Session session : this.sessions) {
			if (today.isEqual(session.getDate())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Acquiert la seance en date du jour.
	 * 
	 * @return La seance en date du jour.
	 */
	public Session getTodaysSession() {
		LocalDate today = LocalDate.now();
		for (Session session : this.sessions) {
			if (today.isEqual(session.getDate())) {
				return session;
			}
		}
		return null;
	}

	public LocalDate getCreationDate() {
		return this.creationDate;
	}

	public String getId() {
		return this.id;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRecurrence(ArrayList<DayOfWeek> recurrence) {
		this.recurrence = recurrence;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setMaxMembers(int maxMembers) {
		this.maxMembers = maxMembers;
	}

	public ArrayList<Session> getSessions() {
		return this.sessions;
	}

	public Professional getProfessional() {
		return this.professional;
	}

	public double getPrice() {
		return this.price;
	}

	public LocalDate getServiceStartDate() {
		return this.serviceStartDate;
	}

	public LocalDate getServiceEndDate() {
		return this.serviceEndDate;
	}

	public ArrayList<DayOfWeek> getRecurrence() {
		return this.recurrence;
	}

	public String getComments() {
		return this.comments;
	}

	public int getMaxMembers() {
		return this.maxMembers;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public LocalDate getRegistrationDate() {
		return this.registrationDate;
	}

	public String getHours(){
		return this.hours;
	}
}
