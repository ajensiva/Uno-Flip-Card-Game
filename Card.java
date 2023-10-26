import java.util.Objects;

/**
 * Card class : Provides methods and attributes for each individual Card
 * @author Jason, Ajen, Arun, Zarif
 * @version 1.0
 */
public class Card {
    /**
     * enum TypeLight : provides all the possible types for a Card when the Card is being played on the light side
     */
    enum TypeLight{
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        WILDTWO (50),
        SKIP(30),
        REVERSE(20),
        DRAW_TWO(10),
        WILD_DRAW_FOUR(60),
        FLIP(20);

        private final int value;
        /**
         * TypeLight : set's the current value to value passed in for a TypeLight
         * @param value
         */
        TypeLight(int value) {
            this.value = value;
        }

        /**
         * getValue : return's the value associated for a given TypeLight
         * @return int
         */
        public int getValue() {
            return this.value;
        }
    }
    /**
     * enum TypeDark: provides all the possible types for a Card when the Card is being played on the dark side
     */
    enum TypeDark{
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        WILDTWO (50),
        SKIP(30),
        REVERSE(20),
        DRAW_TWO(10),
        WILD_DRAW_FOUR(60),
        FLIP(20);
        private final int value;
        /**
         * TypeDark : set's the current value to value passed in for a TypeDark
         * @param value
         */
        TypeDark(int value) {
            this.value = value;
        }
        /**
         * getValue : return's the value associated for a given TypeDark
         * @return int
         */
        public int getValue() {
            return this.value;
        }
      }
    /**
     * enum ColorLight : Provides the possible Colors on a given Card's light side
     */
    enum ColorLight {
        Red, Blue, Green, Yellow
    }
    /**
     * enum ColorDark : Provides the possible Colors on a given Card's dark side
     */
    enum ColorDark {
        Pink, Teal, Purple, Orange
    }

    private  ColorLight colorLight;
    private  TypeLight typeLight;
    private  ColorDark colorDark;
    private  TypeDark typeDark;

    /**
     * Card : Constructor for the Class, takes in parameters to set values for both light and dark side of the card
     * @param colorLight
     * @param typeLight
     * @param colorDark
     * @param typeDark
     */
    public Card(ColorLight colorLight, TypeLight typeLight, ColorDark colorDark, TypeDark typeDark){
        this.colorLight = colorLight;
        this.typeLight = typeLight;
        this.colorDark = colorDark;
        this.typeDark = typeDark;
    }

    /**
     * ColorLight : return's the Card's color on the light side
     * @return ColorLight
     */
    public ColorLight getColorLight() {
        return this.colorLight;
    }
    
    /**
     * SetColorLight : Allow's the card to be modified in case of Wild Card
     * @param newColor
     * @return boolean
     */
    public boolean setColorLight(String newColor) {
       for (ColorLight colors : ColorLight.values()){
           // found the colour im looking for to set
           if ((Objects.equals(newColor, colors.toString()))){
               this.colorLight = colors;
               return true;
           }
       }
       return false;
    }
    /**
     * TypeLight : return's the type on the light side of the card
     * @return TypeLight
     */
    public TypeLight getTypeLight() {
        return this.typeLight;
    }

    /**
     * ColorDark : return's the color on the dark side of a card
     * @return ColorDark
     */
    public ColorDark getColorDark() {
        return this.colorDark;
    }
    
    /**
     * TypeDark : return's the type on the dark side of a card
     * @return TypeDark
     */
    public TypeDark getTypeDark() {
        return this.typeDark;
    }
    
    /**
     * getValue : return's the value depending on which side of the card the current round is being played on
     * @return
     */
    public int getValue(){
        // return the card's associated value
        if(Round.darkmode)
            return this.getTypeDark().getValue();
        else
            return this.getTypeLight().getValue();
    }

    /**
     * toString : method to represent a given card in String format
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(Round.darkmode){
            sb.append("Dark Color: ").append(colorDark).append(", Dark Type: ").append(typeDark);
        }else{
            sb.append("Light Color: ").append(colorLight).append(", Light Type: ").append(typeLight);
        }
        return sb.toString();
    }
}
