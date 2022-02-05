package accounting;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import datacenter.*;

public class ProfessionalNotice {
    private Professional professional;
    private HashMap<LocalDateTime, Registration> servicesToBill = new HashMap<>();

    public ProfessionalNotice(Professional professional) {
        this.professional = professional;
    }

    /**
     * Methode ajoutant un nouveau service a l'avis
     * 
     * @param date          l'heure et la date du service utilise.
     * @param serviceToBill l'objet service utilise contenant toutes les
     *                      informations pour la facture
     */
    public void addService(LocalDateTime date, Registration serviceToBill) {
        this.servicesToBill.put(date, serviceToBill);
    }

    /**
     * Methode creant la facture a l'aide de l'information sur le membre et les
     * services utilises.
     * 
     * @return Retourne la facture en texte
     */
    public String makeBill() {
        StringBuilder bill = new StringBuilder();
        bill.append(this.professional.getName() + System.lineSeparator() + this.professional.getId() + System.lineSeparator()
                + this.professional.getAddress() + System.lineSeparator() + this.professional.getPostalCode()
                + System.lineSeparator() + this.professional.getProvince());

        ArrayList<LocalDateTime> dateListServices = new ArrayList<LocalDateTime>(this.servicesToBill.keySet());
        Collections.sort(dateListServices);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (LocalDateTime date : dateListServices) {
            bill.append(System.lineSeparator() + System.lineSeparator() + date.format(dateFormat)
                    + System.lineSeparator() + servicesToBill.get(date).getRegistrationDate()
                    + System.lineSeparator() + servicesToBill.get(date).getMember().getName()
                    + System.lineSeparator() + servicesToBill.get(date).getMember().getId()
                    + System.lineSeparator() + servicesToBill.get(date).getSession().getCode()
                    + System.lineSeparator() + servicesToBill.get(date).getSession().getService().getPrice());
        }
        return bill.toString();
    }

    public Professional getProfessional(){
        return this.professional;
    }
}
