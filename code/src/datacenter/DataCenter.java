
package datacenter;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import accounting.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;

public class DataCenter {

    private HashMap<String, User> users = new HashMap<>();
    private HashMap<String, Service> serviceCatalog = new HashMap<>();
    private User activeUser;
    Scanner inputCommand = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        DataCenter dataCenter = new DataCenter();
        dataCenter.chooseSimulation();
    }

    /**
     * Permet de choisir si on veut simuler la console de l'agent ou l'application
     * mobile.
     */
    private void chooseSimulation() throws IOException {

        boolean quit = false;
        while (!quit) {
            System.out.println(
                    "Veuillez choisir le comportement à simuler : \n1 - Console de l'agent\n2 - Application mobile\n0 - Quitter");
            String s = this.inputCommand.nextLine();
            switch (s) {
                case "1" -> simulateAgent();
                case "2" -> simulateApp();
                case "0" -> {
                    quit = true;
                    System.out.println("Au revoir!\n Fin du programme.");
                }
                default -> {
                    System.out.println("Option non reconnue, fin du programme.");
                    this.inputCommand.close();
                }
            }
        }
    }

    /**
     * Simule l'application mobile. Fidele aux contraintes du contrat de service,
     * cette fonction peut etre supprimee ou ignoree et l'application mobile livree
     * par la compagnie tierce la remplacera.
     */
    private void simulateApp() throws IOException {
        boolean quit = false;
        String ask;
        String email;
        System.out.println("SIMULATION DE L'APPLICATION MOBILE");
        while (!quit) {
            if (this.activeUser == null) {
                printUserAppMenu();
                ask = this.inputCommand.nextLine();
                switch (ask) {
                    case "1" -> {
                        System.out.println("Veuillez entrer votre courriel Facebook: ");
                        email = this.inputCommand.nextLine();
                        this.activeUser = login(email);
                    }
                    case "0" -> {
                        quit = true;
                        System.out.println("Au revoir!");
                    }
                    default -> System.out.println("Option non reconnue");
                }
            }

            if (this.activeUser instanceof Member) {
                printMemberMenu();
                ask = this.inputCommand.nextLine();
                switch (ask) {
                    case "1" -> {
                        String serviceToday = listServicesWithSessionsToday();
                        if(serviceToday == null){
                            System.out.println("Aucun Service est offert aujourd'hui");
                        }else {
                            System.out.println(serviceToday);
                        }
                    }
                    case "2" -> sessionRegistration();
                    case "3" -> {
                        System.out.println("Vous avez été déconnecté");
                        this.activeUser = null;
                    }
                    case "0" -> {
                        quit = true;
                        System.out.println("Au revoir!");
                    }
                    default -> System.out.println("Option non reconnue");
                }

            } else if (this.activeUser instanceof Professional) {
                printProfessionalMenu();
                ask = this.inputCommand.nextLine();
                switch (ask) {
                    case "1" -> showRegistrations(this.activeUser);
                    case "2" -> sessionRegistrationConfirmation();
                    case "3" -> {
                        System.out.println("Vous avez été déconnecté");
                        this.activeUser = null;
                    }
                    case "0" -> {
                        quit = true;
                        System.out.println("Au revoir!");
                    }
                    default -> System.out.println("Option non reconnue");

                }

            }
        }
        // déconnexion
        this.activeUser = null;
    }

    /**
     * Boucle principale de l'interface avec l'agent.
     */
    private void simulateAgent() throws IOException {
        boolean quit = false;
        String ask;
        String email;

        while (!quit) {
            if (this.activeUser == null) {
                printAgentMenuWithoutLogin();
                ask = this.inputCommand.nextLine();
                switch (ask) {
                    case "1" -> {
                        System.out.println("Veuillez entrer votre courriel facebook: ");
                        email = this.inputCommand.nextLine();
                        this.activeUser = login(email);
                    }
                    case "2" -> accountingProcedure();
                    case "3" -> newUser();
                    case "4" -> managerProcedure();
                    case "0" -> {
                        quit = true;
                        System.out.println("Au revoir!");
                    }
                    default -> System.out.println("Option non reconnue");

                }
            }
            if (this.activeUser instanceof Professional) {
                printAgentMenuProfesionnal();
                ask = this.inputCommand.nextLine();
                switch (ask) {
                    case "1" -> {
                        System.out.println("Veuillez entrer votre courriel facebook: ");
                        email = this.inputCommand.nextLine();
                        this.activeUser = login(email);
                    }
                    case "2" -> accountingProcedure();
                    case "3" -> showRegistrations(this.activeUser);
                    case "4" -> updateInfo(this.activeUser);
                    case "5" -> {
                        deleteUser();
                        this.activeUser = null;
                    }
                    case "6" -> addNewService();
                    case "7" -> serviceModification();
                    case "8" -> newUser();
                    case "9" -> managerProcedure();
                    case "0" -> {
                        quit = true;
                        System.out.println("Au revoir!");
                    }
                    default -> System.out.println("Option non reconnue");

                }
            }

            if (this.activeUser instanceof Member) {
                printAgentMenuMember();
                ask = this.inputCommand.nextLine();
                switch (ask) {
                    case "1" -> {
                        System.out.println("Veuillez entrer votre courriel facebook: ");
                        email = this.inputCommand.nextLine();
                        this.activeUser = login(email);
                    }
                    case "2" -> accountingProcedure();
                    case "3" -> updateInfo(this.activeUser);
                    case "4" -> {
                        deleteUser();
                        this.activeUser = null;
                    }
                    case "5" -> newUser();
                    case "6" -> managerProcedure();
                    case "0" -> {
                        quit = true;
                        System.out.println("Au revoir!");
                    }
                    default -> System.out.println("Option non reconnue");

                }
            }
        }
        // déconnexion
        this.activeUser = null;
    }

    /**
     * Imprime les differentes options que l'agent peut choisir de la console
     * lorsqu'il est connecte avec un compte d'un membre. Sert a des fins
     * d'allegement de lisibilite de code.
     */
    private void printAgentMenuProfesionnal() {
        System.out.println("SIMULATION DE L'INTERFACE DE L'AGENT");
        System.out.println("""
                Veuillez choisir une option :\s
                1 - Connexion\s
                2 - Procédure comptable\s
                3 - Montrer les inscriptions à une séance
                4 - Modifier un utilisateur\s
                5 - Supprimer un utilisateur\s
                6 - Ajouter un service\s
                7 - Modifier un service\s
                8 - Création d'utilisateur\s
                9 - Rapport du gérant\s
                0 - Retour en arrière""");
    }

    /**
     * Imprime les differentes options que l'agent peut choisir de la console
     * lorsqu'il est connecte avec un compte professionel. Sert a des fins
     * d'allegement de lisibilite de code.
     */
    private void printAgentMenuMember() {
        System.out.println("SIMULATION DE L'INTERFACE DE L'AGENT");
        System.out.println("""
                Veuillez choisir une option :\s
                1 - Connexion\s
                2 - Procédure comptable\s
                3 - Modifier un utilisateur\s
                4 - Supprimer un utilisateur\s
                5 - Création d'utilisateur\s
                6 - Rapport du gérant\s
                0 - Retour en arrière""");
    }

    /**
     * Imprime les differentes options que l'agent peut choisir sans etre connecte
     * avec un utilisateur de la console. Sert à des fins d'allegement de lisibilite
     * de code.
     */
    private void printAgentMenuWithoutLogin() {
        System.out.println("SIMULATION DE L'INTERFACE DE L'AGENT");
        System.out.println("""
                Veuillez choisir une option :\s
                1 - Connexion\s
                2 - Procédure comptable\s
                3 - Création d'utilisateur\s
                4 - Rapport du gérant\s
                0 - Retour en arrière""");
    }

    /**
     * Imprime les differentes options que l'utilisateur de l'application peut
     * choisir de la console. Sert a des fins d'allegement de lisibilite de code.
     */
    private void printUserAppMenu() {
        System.out.println("""
                Veuillez sélectionner une option :\s
                1 - Connexion \s
                0 - Retour en arrière""");
    }

    /**
     * Imprime les differentes options qu'un membre peut choisir de la console. Sert
     * a des fins d'allegement de lisibilite de code.
     */
    private void printMemberMenu() {
        System.out.println("""
                Veuillez sélectionner une option :\s
                1 - Catalogue des services \s
                2 - S'inscrire à une séance  \s
                3 - Déconnexion \s
                0 - Retour en arrière""");
    }

    /**
     * Imprime les differentes options qu'un professionel peut choisir de la
     * console. Sert a des fins d'allegement de lisibilite de code.
     */
    private void printProfessionalMenu() {
        System.out.println("""
                Veuillez choisir une option :\s
                1 - Voir les inscriptions à mes séances \s
                2 - Confirmer les inscriptions à une séance  \s
                3 - Déconnexion \s
                0 - Retour en arrière""");
    }

    /**
     * Verifie (ou connecte) un membre ou professionnel.
     *
     * @param email Le courriel du membre.
     * @return Un Membre ou un Professionnel
     */
    private User login(String email) {
        User user = null;

        for (User loopUser : this.users.values()) {
            if (loopUser.getEmail().equals(email)) {
                user = loopUser;
            }
        }
        if (user instanceof User && user.isDeleted()) {
            System.out.println("Utilisateur supprimé");
            return null;
        }
        if (user instanceof Member) {
            if (((Member) user).isSuspended()) {
                System.out.println("Membre suspendu");
                return null;
            } else {
                System.out.println("Validé\n" + user.getName() + "\n#" + user.getId());
                return user;
            }
        }

        if (user instanceof Professional) {
            System.out.println("Professionnel #" + user.getId() + " est connecté");
            return user;
        }

        System.out.println("Courriel invalide");
        return null;
    }

    /**
     * Enregistre une session d'un service.
     */
    private void sessionRegistration() {
        String memberId;
        String serviceId;
        String confirmation;
        String includeComments;
        String comments;
        

        if (activeUser instanceof Member) {
            // normalement fournit par l'application mobile.
            memberId = this.activeUser.getId();
            String sercicesToday =  listServicesWithSessionsToday();
            if( sercicesToday == null ){
                System.out.println("Aucun Service est offert aujourd'hui");
                return ; 
            }

            System.out.println("Veuillez inscrire le numéro du service : ");
            serviceId = this.inputCommand.nextLine();
            while (verifyServiceId(serviceId)) {
                System.out.println("SVP entrer un numéro de service valide.");
                serviceId = this.inputCommand.nextLine();
            }

            System.out.println("Veuillez confirmer votre inscription : O/N ");
            confirmation = this.inputCommand.nextLine();
            switch (confirmation.toUpperCase()) {
                case "O" -> {
                    System.out.println("Voulez-vous préciser des commentaires ? O/N ");
                    includeComments = this.inputCommand.nextLine();
                    switch (includeComments.toUpperCase()) {
                        case "O" -> {
                            comments = this.inputCommand.nextLine();
                            validateInfo("comments", comments);
                            registerForSession(memberId, serviceId, comments);
                            confirmRegistration(memberId, serviceId);
                        }

                        case "N" -> {
                            registerForSession(memberId, serviceId, "");
                            confirmRegistration(memberId, serviceId);
                        }

                        default -> System.out.println("Inscription annulée");
                    }
                }

                case "N" -> System.out.println("Inscription annulée");

                default -> System.out.println("Inscription annulée");

            }
        } else {
            System.out.println("Un membre doit être connecté.");
        }
    }

    /**
     * Permet au professionnel de confirmer les inscriptions a sa seance
     */
    private void sessionRegistrationConfirmation() {
        String memberId;
        String serviceId;
        if (this.activeUser instanceof Professional) {

            Professional professional = (Professional) this.activeUser;
            professional.listServices();

            System.out.println("Voulez-vous confirmer les inscriptions d'une séance? (O/N)");
            String conf = inputCommand.nextLine();
            if (conf.equalsIgnoreCase("O")) {
                System.out.println("Veuillez entrer le code de la séance");
                String sessionCode = inputCommand.nextLine();
                serviceId = sessionCode.substring(0, 3);
                while (verifyServiceId(serviceId)) {
                    System.out.println("Veuillez entrer un code de séance valide.");
                    sessionCode = inputCommand.nextLine();
                    serviceId = sessionCode.substring(0, 3);
                }

                System.out.println("Veuillez entrer le code QR (ID) du membre");
                memberId = inputCommand.nextLine();
                while (verifyUserId(memberId)) {
                    System.out.println("Veuillez entrer un numéro de membre valide.");
                    memberId = inputCommand.nextLine();
                }

                if (serviceCatalog.get(serviceId).getTodaysSession()
                        .getRegistrationForMember((Member) this.users.get(memberId)) == null) {
                    System.out.println("L'utilisateur n'est pas enregistrer pour cette séance");
                } else if (serviceCatalog.get(serviceId).getTodaysSession()
                        .getRegistrationForMember((Member) this.users.get(memberId)).isConfirmed()) {
                    System.out.println("Validé");
                } else {
                    System.out.println("Accès refusé");
                }
            }

        } else {
            System.out.println("Un professionnel doit se connecter.");
        }

    }

    /**
     * Imprime les inscriptions aux seances d'un professionnel sur la console
     *
     * @param activeUser Le professionnel pour qui les inscriptions seront
     *                   affichees.
     */
    private void showRegistrations(User activeUser) {
        if (activeUser instanceof Professional) {
            for (String key : serviceCatalog.keySet()) {
                for (Session session : serviceCatalog.get(key).getSessions()) {
                    if (session.getDate().isAfter(LocalDate.now()) || session.getDate().isEqual(LocalDate.now())) {
                        session.listRegistrationsForProfessionnal(((Professional) activeUser));
                    }
                }
            }
        } else {
            System.out.println("Le client n'a pas les droits.");
        }
    }

    /**
     * Liste tous les services qui ont une seance en date du jour.
     *
     * @return Une chaine contenant tous les noms des services ayant une seance en
     *         date du jour ainsi que leur code de service. Retourne null si vide.
     */
    public String listServicesWithSessionsToday() {
        String message = "Les services suivants sont offerts aujourd'hui:\n";
        String sessionsTodayMessage = "";
        LocalDate today = LocalDate.now();
        LocalDate sessionDate;

        for (Service service : this.serviceCatalog.values()) {
            if (service.hasSessionToday()) {
                sessionsTodayMessage += service.getName() + " Service #" + service.getId() + "\n";
                for (Session session : service.getSessions()) {
                    sessionDate = session.getDate();
                    if (sessionDate.isEqual(today)) {
                        sessionsTodayMessage += "Séance #" + session.getCode() + "\n";
                    }
                }
            }
        }
        if (!sessionsTodayMessage.equals("")) {
            return message + sessionsTodayMessage;  
        } else {
            return null;
        }
    }

    /**
     * Ajoute une inscription a une seance pour un membre.
     *
     * @param memberId  Le code du membre.
     * @param serviceId Le code du service qui a en date du jour une seance a
     *                  laquelle le membre veut s'inscrire.
     * @param comments  Des commentaires facultatifs.
     */
    public void registerForSession(String memberId, String serviceId, String comments) {
        Registration registration = new Registration(LocalDateTime.now(), ((Member) this.users.get(memberId)),
                this.serviceCatalog.get(serviceId).getProfessional(),
                this.serviceCatalog.get(serviceId).getTodaysSession(), comments);
        this.serviceCatalog.get(serviceId).getTodaysSession().addRegistration(registration);
    }

    /**
     * Confirme une inscription pour un membre.
     *
     * @param memberId  Le code du membre qui s'est inscrit a la seance.
     * @param serviceId Le code du service de la seance
     */
    public void confirmRegistration(String memberId, String serviceId) {
        this.serviceCatalog.get(serviceId).getTodaysSession()
                .getRegistrationForMember((Member) this.users.get(memberId)).confirmation();
        System.out.println("Vous devez " + this.serviceCatalog.get(serviceId).getPrice() + "$ au professionnel.");
    }

    /**
     * Permet a l'agent d'entrer les informations necessaires pour la creation d'un
     * nouveau membre ou professionnel.
     */
    private void newUser() {
        String name;
        String phoneNumber;
        String email;
        String address;
        String province;
        String city;
        String postalCode;

        // Enregistre les informations personnelles du client
        System.out.println("Veuillez entrer le nom de l'utilisateur");
        name = this.inputCommand.nextLine();
        name = validateInfo("name", name);

        System.out.println("Veuillez entrer le numéro de téléphone de l'utilisateur");
        phoneNumber = this.inputCommand.nextLine();
        phoneNumber = validateInfo("phone number", phoneNumber);

        System.out.println("Veuillez entrer l'adresse courriel de l'utilisateur");
        email = this.inputCommand.nextLine();


        System.out.println("Veuillez entrer l'adresse de l'utilisateur");
        address = this.inputCommand.nextLine();
        address = validateInfo("address", address);

        System.out.println("Veuillez entrer le code de la province de l'utilisateur");
        province = this.inputCommand.nextLine();
        province = validateInfo("province", province);

        System.out.println("Veuillez entrer la ville de l'utilisateur");
        city = this.inputCommand.nextLine();
        city = validateInfo("city", city);

        System.out.println("Veuillez entrer le code postal de l'utilisateur");
        postalCode = this.inputCommand.nextLine();
        postalCode = validateInfo("postal code", postalCode);

        // Crée un profesionnel ou un membre
        System.out.println("Choisissez le type d'utilisateur à créer : \n1 - Membre\n2 - Professionnel");
        String askMemberType = this.inputCommand.nextLine();

        switch (askMemberType) {
            case "1" -> {
                User member = new Member(name, email, phoneNumber, address, province, city, postalCode);
                System.out.println("Le membre #" + member.getId() + " a été créé avec succès!");
                this.users.put(member.getId(), member);
            }

            case "2" -> {
                User professional = new Professional(name, email, phoneNumber, address, province, city, postalCode);
                System.out.println("Le professionnel #" + professional.getId() + " a été créé avec succès!");
                this.users.put(professional.getId(), professional);
            }

            default -> System.out.println("Option non reconnue");
        }
    }

    /**
     * Verifie que les informations entrees par l'utilisateur respectent les
     * contraintes demandees par l'application.
     *
     * @param infoType       Le type de l'information a verifier
     * @param infoToValidate L'information a valider
     * @return L'information sous forme validee
     */
    private String validateInfo(String infoType, String infoToValidate) {
        String modifiedName;
        String modifiedPhoneNumber;
        String modifiedCity;
        String modifiedAddress;
        String modifiedPostalCode;
        String modifiedComments;
        String modifiedCapacity;
        String validatedInfo;

        switch (infoType.toLowerCase()) {
            case "name":
                if (infoToValidate.length() > 30 || infoToValidate.length() == 0) {
                    System.out.println("Veuillez entrer le nom de l'utilisateur en 30 caractères ou moins");
                    modifiedName = inputCommand.nextLine();
                    if (modifiedName.length() > 30 || infoToValidate.length() == 0) {
                        validateInfo("name", modifiedName);
                    }
                    return modifiedName;
                } else {
                    break;
                }

            case "phone number":
                if (infoToValidate.length() != 10 || !(infoToValidate.chars().allMatch(Character::isDigit))) {
                    System.out.println("Veuillez entrer un numéro de téléphone valide");
                    modifiedPhoneNumber = inputCommand.nextLine();
                    if (modifiedPhoneNumber.length() != 10
                            || !(modifiedPhoneNumber.chars().allMatch(Character::isDigit))) {
                        validateInfo("phone number", modifiedPhoneNumber);
                    }
                    return modifiedPhoneNumber;
                } else {
                    break;
                }

            case "address":
                if (infoToValidate.length() > 25 || infoToValidate.length() == 0) {
                    System.out.println("Veuillez entrer une addresse valide");
                    modifiedAddress = inputCommand.nextLine();
                    if (modifiedAddress.length() > 25 || infoToValidate.length() == 0) {
                        validateInfo("address", modifiedAddress);
                    }
                    return modifiedAddress;
                } else {
                    break;
                }

            case "city":
                if (infoToValidate.length() > 14 || infoToValidate.length() == 0) {
                    System.out.println("Veuillez entrer une ville valide");
                    modifiedCity = inputCommand.nextLine();
                    if (modifiedCity.length() > 14 || infoToValidate.length() == 0) {
                        validateInfo("city", modifiedCity);
                    }
                    return modifiedCity;
                } else {
                    break;
                }

            case "province":
                if (infoToValidate.length() != 2) {
                    System.out.println("Veuillez entrer un code de la province valide (2 lettres)");
                    String modifiedProvince = inputCommand.nextLine();
                    if (modifiedProvince.length() != 2) {
                        validateInfo("province", modifiedProvince);
                    }
                    return modifiedProvince;
                } else {
                    break;
                }

            case "postal code":
                if (infoToValidate.length() > 6 || infoToValidate.length() == 0) {
                    System.out.println("Veuillez entrer un code postal valide");
                    modifiedPostalCode = inputCommand.nextLine();
                    if (modifiedPostalCode.length() > 6 || infoToValidate.length() == 0) {
                        validateInfo("postal code", modifiedPostalCode);
                    }
                    return modifiedPostalCode;
                } else {
                    break;
                }

            case "comments":
                if (infoToValidate.length() > 100) {
                    System.out.println("Veuillez entrer un code postal valide");
                    modifiedComments = inputCommand.nextLine();
                    if (modifiedComments.length() > 100) {
                        validateInfo("comments", modifiedComments);
                    }
                    return modifiedComments;
                } else {
                    break;
                }

            case "capacity":
                if (Integer.parseInt(infoToValidate) > 30 || Integer.parseInt(infoToValidate) == 0) {
                    System.out.println("Vous pouvez prévoir une capacité maximale de 30 participants."
                            + "Veuillez insérer une capacité valide");
                    modifiedCapacity = inputCommand.nextLine();
                    if (modifiedCapacity.length() > 100 || Integer.parseInt(infoToValidate) == 0) {
                        validateInfo("comments", modifiedCapacity);
                    }
                    return modifiedCapacity;
                } else {
                    break;
                }
            default:
                System.out.println(infoType + " ne peut pas être validé");
        }
        validatedInfo = infoToValidate;
        return validatedInfo;
    }

    /**
     * Verifie si une date respecte le format specifie.
     *
     * @param format         Le format a respecter.
     * @param dateToValidate La date a verifier.
     * @return Retourne un booleen
     */
    private boolean dateFormatIsValid(String format, String dateToValidate) {
        LocalDate specifiedDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            specifiedDate = LocalDate.parse(dateToValidate, formatter);
            String result = specifiedDate.format(formatter);
            return (result.equals(dateToValidate) && specifiedDate.isAfter(LocalDate.now().minusDays(1)));
        } catch (DateTimeParseException e) {
            System.err.println("erreur dans le format de date");
        }
        return false;
    }

    /**
     * Verifie si un utilisateur est lie au numero d'utilisateur fourni.
     *
     * @param userId Numero de l'utilisateur a verifier.
     * @return Retourne un booléen
     */
    private boolean verifyUserId(String userId) {
        return this.users.get(userId) == null;
    }

    /**
     * Verifie si service est lie au numero de service fourni.
     *
     * @param serviceId numero du service a verifier.
     * @return Retourne un booleen
     */
    private boolean verifyServiceId(String serviceId) {
        return this.serviceCatalog.get(serviceId) == null;
    }

    /**
     * Permet de modifier les informations personnelles du membre ou du
     * professionnel.
     *
     * @param activeUser L'utilisateur actif qui souhaite modifier ses informations
     *                   personnelles
     */
    private void updateInfo(User activeUser) {
        String infoTomodify;
        String modifiedName;
        String modifiedPhoneNumber;
        String modifiedAddress;
        String modifiedEmail;
        String modifiedProvince;
        String modifiedCity;
        String modifiedPostalCode;
        if (this.activeUser == null) {
            System.out.println("Vous devez vous login");
        }
        printUpdateInfoMenu();
        infoTomodify = inputCommand.nextLine();
        switch (infoTomodify) {
            case "1" -> {
                System.out.println("Veuillez entrer la modification du nom");
                modifiedName = inputCommand.nextLine();
                modifiedName = validateInfo("name", modifiedName);
                activeUser.setName(modifiedName);
                System.out.println("La modification du nom a été effecutée!");
            }
            case "2" -> {
                System.out.println("Veuillez entrer le courriel modifié");
                modifiedEmail = inputCommand.nextLine();
                activeUser.setEmail(modifiedEmail);
                System.out.println("La modification du courriel a été effecutée!");
            }
            case "3" -> {
                System.out.println("Veuillez entrer le numéro de téléphone modifié");
                modifiedPhoneNumber = inputCommand.nextLine();
                modifiedPhoneNumber = validateInfo("phone number", modifiedPhoneNumber);
                activeUser.setPhoneNumber(modifiedPhoneNumber);
                System.out.println("La modification du numéro de téléphone a été effecutée!");
            }
            case "4" -> {
                System.out.println("Veuillez entrer l'adresse modifiée");
                modifiedAddress = inputCommand.nextLine();
                modifiedAddress = validateInfo("address", modifiedAddress);
                activeUser.setAddress(modifiedAddress);
                System.out.println("La modification de l'adresse a été effecutée!");
            }
            case "5" -> {
                System.out.println("Veuillez entrer la province modifiée");
                modifiedProvince = inputCommand.nextLine();
                modifiedProvince = validateInfo("province", modifiedProvince);
                activeUser.setProvince(modifiedProvince);
                System.out.println("La modification de la province a été effecutée!");
            }
            case "6" -> {
                System.out.println("Veuillez entrer la ville modifiée");
                modifiedCity = inputCommand.nextLine();
                modifiedCity = validateInfo("city", modifiedCity);
                activeUser.setCity(modifiedCity);
                System.out.println("La modification de la ville a été effecutée!");
            }
            case "7" -> {
                System.out.println("Veuillez entrer le code postal modifié");
                modifiedPostalCode = inputCommand.nextLine();
                modifiedPostalCode = validateInfo("postal code", modifiedPostalCode);
                activeUser.setPostalCode(modifiedPostalCode);
                System.out.println("La modification du code postal a été effecutée!");
            }
        }
    }

    /**
     * Imprime les differentes options d'informations personnelles d'utilisateur que
     * l'agent peut modifier. Sert a des fins d'allegement de lisibilite de code.
     */
    private void printUpdateInfoMenu() {
        System.out.println("""
                Quelle information souhaitez-vous modifier ?\s
                1- Nom\s
                2- Email\s
                3- Numéro de téléphone\s
                4- Adresse\s
                5- Province\s
                6- Ville\s
                7- Code Postal""");
    }

    /**
     * Permet a l'agent de supprimer un membre.
     * 
     */
    private void deleteUser() {
        if (this.activeUser != null) {
            if (this.activeUser.isDeleted()) {
                System.out.println("Cette utilisateur a déjà été supprimé!");
            } else {
                this.activeUser.deleteUser();
                System.out.println("L'utilisateur a été supprimé!");
            }
        } else {
            System.out.println("Un utilisateur doit être connecté.");
        }
    }

    /**
     * Permet a l'agent d'entrer les informations necessaires pour la creation d'un
     * nouveau service.
     */
    private void addNewService() {
        String dateFormat = "yyyy-MM-dd";
        if (activeUser instanceof Professional) {
            Professional professional = (Professional) activeUser;

            System.out.println("Veuillez entrer le nom du service");
            String name = this.inputCommand.nextLine();

            // Valide la date de début de service
            System.out.println("Veuillez entrer la date de début du service (AAAA-MM-JJ)");
            String serviceStartDate = this.inputCommand.nextLine();

            while (!dateFormatIsValid(dateFormat, serviceStartDate)) {
                System.out.println("La date n'est pas valide ! Veuillez écrire une date dans le format AAAA-MM-JJ.");
                serviceStartDate = this.inputCommand.nextLine();
            }
            int bYear = Integer.parseInt(serviceStartDate.split("-")[0]);
            int bMonth = Integer.parseInt(serviceStartDate.split("-")[1]);
            int bDay = Integer.parseInt(serviceStartDate.split("-")[2]);
            LocalDate validatedServiceStartDate = LocalDate.of(bYear, bMonth, bDay);

            // Valide la date de fin de service
            System.out.println("Veuillez entrer la date de fin du service (AAAA-MM-JJ)");
            String serviceEndDate = this.inputCommand.nextLine();
            dateFormatIsValid(dateFormat, serviceEndDate);
            while (!dateFormatIsValid(dateFormat, serviceEndDate)) {
                System.out.println("La date n'est pas valide ! Veuillez écrire une date dans le format AAAA-MM-JJ.");
                serviceEndDate = this.inputCommand.nextLine();
            }
            int eYear = Integer.parseInt(serviceEndDate.split("-")[0]);
            int eMonth = Integer.parseInt(serviceEndDate.split("-")[1]);
            int eDay = Integer.parseInt(serviceEndDate.split("-")[2]);
            LocalDate validatedServiceEndDate = LocalDate.of(eYear, eMonth, eDay);
            // Valide que la date de fin de service se déroule après la date de début
            while (validatedServiceEndDate.isBefore(validatedServiceStartDate)
                    || validatedServiceEndDate.isEqual(validatedServiceStartDate)) {
                System.out.println("La date de fin doit être après la date de début!");
                serviceEndDate = this.inputCommand.nextLine();
                dateFormatIsValid(dateFormat, serviceEndDate);
                while (!dateFormatIsValid(dateFormat, serviceEndDate)) {
                    System.out
                            .println("La date n'est pas valide ! Veuillez écrire une date dans le format AAAA-MM-JJ.");
                    serviceEndDate = this.inputCommand.nextLine();
                }
                eYear = Integer.parseInt(serviceEndDate.split("-")[0]);
                eMonth = Integer.parseInt(serviceEndDate.split("-")[1]);
                eDay = Integer.parseInt(serviceEndDate.split("-")[2]);
                validatedServiceEndDate = LocalDate.of(eYear, eMonth, eDay);
            }

            System.out.println("Veuillez entrer les heures auxquelles les séances auront lieu (HH:MM)");
            String hours = this.inputCommand.nextLine();

            System.out.println(
                    "Veuillez entrer les jours auxquels les séances auront lieu (1 pour lundi, 13 pour lundi et mercredi, 1234567 pour toute la semaine)"); // on

            String[] days = this.inputCommand.nextLine().split("");

            ArrayList<DayOfWeek> recurrence = new ArrayList<>();

            for (String day : days) {
                recurrence.add(DayOfWeek.of(Integer.parseInt(day)));
            }

            System.out.println("Veuillez entrer la capacité maximale");
            int maximumCapacity = Integer.parseInt(this.inputCommand.nextLine());
            maximumCapacity = Integer.parseInt(validateInfo("capacity", (String.valueOf(maximumCapacity))));

            System.out.println("Veuillez entrer le prix de participation à une séance");
            double price = Double.parseDouble(this.inputCommand.nextLine());

            System.out.println("Veuillez entrer les commentaires (facultatif)");
            String comments = this.inputCommand.nextLine();
            comments = validateInfo("comments", comments);

            Service service = new Service(LocalDate.now(), validatedServiceStartDate, validatedServiceEndDate,
                    recurrence, name, professional, comments, maximumCapacity, price, hours);
            this.serviceCatalog.put(service.getId(), service);
            professional.addService(service);
            System.out.println("Le service #" + service.getId() + " a été créé!");
        } else {
            System.out.println("Veuillez connecter un professionnel.");
        }
    }

    /**
     * Modifie les informations d'un service ou supprime un service.
     */
    private void serviceModification() {
        String serviceIdToDelete;
        String typeOfModification;
        String infoToModify;
        String modifiedName;
        String modifiedComments;
        int modifiedCapacity;
        int modifiedPrice;

        if (this.activeUser == null) {
            System.out.println("Vous devez vous connecter.");

        } else if (this.activeUser instanceof Professional) {
            System.out.println("Voulez-vous 1-modifier ou 2-supprimer un service?");
            typeOfModification = this.inputCommand.nextLine();
            switch (typeOfModification) {
                case "1" -> {
                    System.out.println("Veuillez entrer le ID du service à modifier");
                    serviceIdToDelete = this.inputCommand.nextLine();
                    Service serviceToModify = this.serviceCatalog.get(serviceIdToDelete);
                    printServiceModificationMenu();
                    infoToModify = this.inputCommand.nextLine();
                    switch (infoToModify) {
                        case "1" -> {
                            System.out.println("Veuillez entrer le nouveau nom du service");
                            modifiedName = this.inputCommand.nextLine();
                            modifiedName = validateInfo("name", modifiedName);
                            serviceToModify.setName(modifiedName);
                            System.out.println("Le nom du service a été modifié !");
                        }
                        case "2" -> {
                            System.out.println("Veuillez entrer les nouveaux commentaires du service");
                            modifiedComments = this.inputCommand.nextLine();
                            modifiedName = validateInfo("comments", modifiedComments);
                            serviceToModify.setName(modifiedName);
                            System.out.println("Les commentaires du service a été modifié !");
                        }
                        case "3" -> {
                            System.out.println("Veuillez entrer la nouvelle capacité maximale du service");
                            modifiedCapacity = Integer.parseInt(this.inputCommand.nextLine());
                            modifiedCapacity = Integer
                                    .parseInt(validateInfo("capacity", String.valueOf(modifiedCapacity)));
                            serviceToModify.setMaxMembers(modifiedCapacity);
                            System.out.println("La capacité maximale du service a été modifiée !");
                        }
                        case "4" -> {
                            System.out.println("Veuillez entrer le nouveau prix du service");
                            modifiedPrice = Integer.parseInt(this.inputCommand.nextLine());
                            serviceToModify.setPrice(modifiedPrice);
                            System.out.println("Le prix du service a été modifié !");
                        }
                        case "5" -> {
                            System.out.println("Veuillez entrer les jours auxquels les séances auront lieu "
                                    + "(1 pour lundi, 13 pour lundi et mercredi, 1234567 pour toute la semaine)\"");
                            String[] days = this.inputCommand.nextLine().split("");
                            ArrayList<DayOfWeek> modifiedRecurrence = new ArrayList<>();
                            for (String day : days) {
                                modifiedRecurrence.add(DayOfWeek.of(Integer.parseInt(day)));
                                serviceToModify.setRecurrence(modifiedRecurrence);
                            }
                            serviceToModify.removeFutureSessions();
                            serviceToModify.addSessions();
                            System.out.println("La récurrence du service a été modifiée !");
                        }

                        default -> System.out.println("Cette modification n'est pas possible");
                    }
                }
                case "2" -> {
                    System.out.println("Veuillez entrer le ID du service à supprimer");
                    serviceIdToDelete = this.inputCommand.nextLine();
                    deleteService(serviceIdToDelete);
                }
            }
        } else {
            System.out.println("Le client n'a pas les droits.");
        }
    }

    /**
     * Imprime les differentes options d'informations d'un service que l'agent peut
     * modifier. Sert à des fins d'allegement de lisibilite de code.
     */
    private void printServiceModificationMenu() {
        System.out.println("""
                Veuillez spécifier l'information que vous souhaitez modifier:\s
                1- nom\s
                2- commentaires\s
                3- capacité maximale\s
                4- prix\s
                5- reccurence""");
    }

    /**
     * Effectue la suppression d'un service.
     */
    private void deleteService(String id) {
        serviceCatalog.remove(id);
        System.out.println("Le service a été supprimé!");
    }

    /**
     * Methode qui passe a travers les Service et lie les donnees des autres objets
     * relatifs (Session, Member et Professional) pour regrouper ces informations
     * dans de nouveaux objets qui servirons a creer les rapports. Une fois les
     * informations regroupees, la creation de rapport se fait sur le disque. Les
     * rapports crees sont ceux des membres, professionel, TEF et celui du gerant.
     *
     * @throws IOException Exception si le fichier n'a pas pu etre ecris.
     */
    private void accountingProcedure() throws IOException {
        HashMap<String, ProfessionalRapport> tefData = new HashMap<>();
        HashMap<String, MemberBill> memberBills = new HashMap<>();
        HashMap<String, ProfessionalNotice> professionalNotices = new HashMap<>();
        String idTemp;
        ProfessionalRapport tefTemp;
        MemberBill memberBillTemp;
        ProfessionalNotice professionalNoticeTemp;

        for (Service service : serviceCatalog.values()) {
            if (LocalDate.now().minusDays(8).isBefore(service.getServiceEndDate())
                    && LocalDate.now().isAfter(service.getServiceStartDate().minusDays(1))) {
                for (Session session : service.getSessions()) {
                    for (Registration registration : session.getRegistrations()) {
                        // S'assurer que les enregistrement au séance soit de la bonne date et
                        // confirmer.
                        long dayAppart = Duration
                                .between(LocalDate.now().atStartOfDay(), registration.getRegistrationDate()).toDays();
                        if (registration.isConfirmed() && dayAppart >= 0 && dayAppart <= 7) {
                            // Partie membre
                            idTemp = registration.getMember().getId();
                            memberBillTemp = memberBills.get(idTemp);
                            if (memberBillTemp == null) {
                                memberBillTemp = new MemberBill(registration.getMember());
                                memberBills.put(idTemp, memberBillTemp);
                            }
                            memberBills.get(idTemp).addService(registration.getRegistrationDate(), registration);

                            // Partie professionel
                            idTemp = registration.getProfessional().getId();
                            professionalNoticeTemp = professionalNotices.get(idTemp);
                            if (professionalNoticeTemp == null) {
                                professionalNoticeTemp = new ProfessionalNotice(registration.getProfessional());
                                professionalNotices.put(idTemp, professionalNoticeTemp);
                            }
                            professionalNotices.get(idTemp).addService(registration.getRegistrationDate(),
                                    registration);

                            // Partie TEF
                            tefTemp = tefData.get(idTemp);
                            if (tefTemp == null) {
                                tefTemp = new ProfessionalRapport(registration.getProfessional().getName(), idTemp);
                                tefData.put(idTemp, tefTemp);
                            }
                            tefData.get(idTemp).addAmount(registration.getSession().getService().getPrice());
                        }
                    }

                }

            }

        }
        String fileName;
        // Création des fichiers membres
        for (MemberBill memberBill : memberBills.values()) {
            System.out.println("yeah");
            fileName = memberBill.getMember().getName() + "_" + LocalDate.now() + ".txt";
            saveFiles("Membres_Factures", fileName, memberBill.makeBill());
        }
        // Création des fichiers professionnel
        for (ProfessionalNotice professionalNotice : professionalNotices.values()) {
            fileName = professionalNotice.getProfessional().getName() + "_" + LocalDate.now() + ".txt";
            saveFiles("Professional_Notice", fileName, professionalNotice.makeBill());
        }
        int totalServices = 0;
        int totalWeekCost = 0;
        StringBuilder managerRapport = new StringBuilder();
        // Création des fichiers TEF et préparer le rapport gérant
        for (ProfessionalRapport professionalRapport : tefData.values()) {
            fileName = professionalRapport.getName() + "_TEF_" + LocalDate.now() + ".txt";
            saveFiles("TEF", fileName, professionalRapport.makeTEF());
            totalServices += professionalRapport.getNumberOfServices();
            totalWeekCost += professionalRapport.getAmountTopay();
            managerRapport.append(professionalRapport.makeManagerRapport());
        }

        // RapportGérant
        fileName = "Rapport_Gérant_" + LocalDate.now() + ".txt";
        managerRapport.append("Nombre de services total pour la semaine: " + totalServices + System.lineSeparator()
                + "Coût Total pour la semaine: " + totalWeekCost + "$");
        saveFiles("Rapport_Gérant", fileName, managerRapport.toString());

    }

    /**
     * Methode qui passe a travers tous les Service et lie les donnees des autres
     * objets (Session, Member et Professional) pour regrouper ces informations dans
     * de nouveaux objets qui servirons a creer le rapport. Une fois les
     * informations regroupees, le rapport pour le gerant est cree.
     *
     * @throws IOException Exception si le fichier n'a pas pu etre ecris.
     */
    private void managerProcedure() throws IOException {
        LocalDate endDate = LocalDate.now().plusDays(1);
        LocalDate startDate = endDate.minusDays(endDate.getDayOfWeek().getValue())
                .plusDays(DayOfWeek.FRIDAY.getValue());
        // Utilisation de la négation pour assurer d'avoir plus petit (et non égale)
        if (!startDate.isBefore(endDate)) {
            startDate = startDate.minusWeeks(1);
        }

        HashMap<String, ProfessionalRapport> tefData = new HashMap<>();
        String idTemp;
        ProfessionalRapport tefTemp;

        for (Service service : serviceCatalog.values()) {
            if (endDate.isBefore(service.getServiceEndDate())
                    && startDate.isAfter(service.getServiceStartDate().minusDays(1))) {
                for (Session session : service.getSessions()) {
                    for (Registration registration : session.getRegistrations()) {
                        // S'assurer que les enregistrement au séance soit de la bonne date et
                        // confirmer.
                        long dayAppart = Duration
                                .between(LocalDate.now().atStartOfDay(), registration.getRegistrationDate()).toDays();
                        if (registration.isConfirmed() && dayAppart >= 0 && dayAppart <= 7) {
                            idTemp = registration.getProfessional().getId();
                            tefTemp = tefData.get(idTemp);
                            if (tefTemp == null) {
                                tefTemp = new ProfessionalRapport(registration.getProfessional().getName(), idTemp);
                                tefData.put(idTemp, tefTemp);
                            }
                            tefData.get(idTemp).addAmount(registration.getSession().getService().getPrice());
                        }
                    }

                }

            }

        }
        int totalServices = 0;
        int totalWeekCost = 0;
        StringBuilder managerRapport = new StringBuilder();

        // Création des fichiers TEF et préparer le rapport gérant
        for (ProfessionalRapport professionalRapport : tefData.values()) {
            totalServices += professionalRapport.getNumberOfServices();
            totalWeekCost += professionalRapport.getAmountTopay();
            managerRapport.append(professionalRapport.makeManagerRapport());
        }

        // RapportGérant
        String fileName = "Rapport_Gérant_du_" + startDate + "_au_" + endDate + ".txt";
        managerRapport.append("Nombre de services total pour les dates: " + totalServices + System.lineSeparator()
                + "Coût Total pour les dates: " + totalWeekCost + "$");
        saveFiles("Rapport_Gérant", fileName, managerRapport.toString());

    }

    /**
     * Assure qu'un fichier .txt est nouvellement creer (supprimer et creer si
     * necessaire) dans le repertoire mentionne.
     *
     * @param folderName le sous fichier à ajouter
     * @param fileName   nom du fichier (.text sera ajouter à celui-ci)
     * @param text       text à écrire dans le ficher
     * @throws IOException renvoie l'exception s'il a été impossible d'écrire dans
     *                     le fichier
     */
    static void saveFiles(String folderName, String fileName, String text) throws IOException {
        // créer le path.
        File folder = new File(".\\" + folderName);
        folder.mkdir();
        File file = new File(folder.getPath() + "\\" + fileName);
        // supprimer et recrée le fichier.
        file.delete();
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file.getPath());
        fileWriter.write(text);
        fileWriter.close();
    }
}
