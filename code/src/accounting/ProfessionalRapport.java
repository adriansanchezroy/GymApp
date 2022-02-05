package accounting;

public class ProfessionalRapport {
    private String name;
    private String id;
    private double amountToPay ;
    private int numberOfServices;

        public ProfessionalRapport(String name, String id ){
            this.name = name;
            this.id = id;
            this.amountToPay = 0;
            this.numberOfServices =0;
        }

        /**
         * Permet d'augmenter la valeur de la facture et augemente le nombre de service de 1.
         * @param amountToAdd nouvelle valeur monetaire a entrer pour augmenter la facture
         */
        public void addAmount(double amountToAdd){
            this.amountToPay += amountToAdd;
            this.numberOfServices += 1;
        }

        /**
         * Fait un rapport TEF
         * @return une chaine de characteres qui represente le rapport TEF
         */
        public String makeTEF (){
            return this.name +" " + this.id +" " + this.amountToPay +"$";
        }
        /**
         * Fait le rapport manager (
         * @return une chaine de characteres qui represente le rapport au manager
         */
        public String makeManagerRapport (){
            return this.name +" " + this.numberOfServices +"Services " + this.amountToPay+"$" + System.lineSeparator();
        }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }


    public double getAmountTopay() {
        return this.amountToPay;
    }


    public int getNumberOfServices() {
        return this.numberOfServices;
    }
}
