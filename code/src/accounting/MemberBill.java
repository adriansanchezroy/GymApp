package accounting;

import datacenter.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

public class MemberBill {
    private Member member;
    private HashMap<LocalDateTime, Registration> servicesToBill = new HashMap<>();

    public MemberBill(Member member) {
        this.member = member;
    }

    /**
     * Methode ajoutant un nouveau service a la facture
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
     * service utilise.
     * 
     * @return Retourne la facture en texte
     */
    public String makeBill() {
        StringBuilder bill = new StringBuilder();
        bill.append(this.member.getName() + System.lineSeparator() + this.member.getId() + System.lineSeparator()
                + this.member.getAddress() + System.lineSeparator() + this.member.getPostalCode()
                + System.lineSeparator() + this.member.getProvince());

        ArrayList<LocalDateTime> dateListServices = new ArrayList<LocalDateTime>(this.servicesToBill.keySet());
        Collections.sort(dateListServices);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (LocalDateTime date : dateListServices) {
            bill.append(System.lineSeparator() + System.lineSeparator() + date.format(dateFormat)
                    + System.lineSeparator() + servicesToBill.get(date).getProfessional().getName()
                    + System.lineSeparator() + servicesToBill.get(date).getSession().getService().getName());
        }
        return bill.toString();
    }

    public Member getMember(){
        return this.member;
    }
}
