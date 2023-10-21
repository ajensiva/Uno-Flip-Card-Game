import java.util.Objects;

public class Card {

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
        TypeLight(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

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
        TypeDark(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
      }

    enum ColorDark {
        Pink, Teal, Purple, Orange
    }

    enum ColorLight {
        Red, Blue, Green, Yellow
    }

    private  ColorLight colorLight;
    private  TypeLight typeLight;
    private  ColorDark colorDark;
    private  TypeDark typeDark;

    Card(ColorLight colorLight, TypeLight typeLight, ColorDark colorDark, TypeDark typeDark){
        this.colorLight = colorLight;
        this.typeLight = typeLight;
        this.colorDark = colorDark;
        this.typeDark = typeDark;
    }

    public ColorLight getColorLight() {
        return this.colorLight;
    }
    public boolean setColorLight(String user) {

       for (ColorLight colors : ColorLight.values()){
           if (!(Objects.equals(user, colors.toString()))){
               return false;
           }

       }

       return true;
    }


    public TypeLight getTypeLight() {
        return this.typeLight;
    }

    public ColorDark getColorDark() {
        return this.colorDark;
    }

    public TypeDark getTypeDark() {
        return this.typeDark;
    }

    public int getValue(){
        if(Round.darkmode)
            return this.getTypeDark().getValue();
        else
            return this.getTypeLight().getValue();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Light side: [Color: ").append(colorLight).append(", Type: ").append(typeLight).append("], Dark side: [Color: ").append(colorDark).append(", Type: ").append(typeDark).append("]");
        return sb.toString();
    }
}
