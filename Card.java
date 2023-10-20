
public class Card {

    enum TypeLight{ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, WILDTWO, SKIP, REVERSE, DRAW_TWO, WILD_DRAW_FOUR, FLIP}

    enum TypeDark{ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, WILDDRAW, SKIP, REVERSE, DRAW_TWO, WILD_DRAW_FOUR, FLIP}

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
           if (!(user == colors.toString())){
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Light side: [Color: ").append(colorLight)
                .append(", Type: ").append(typeLight)
                .append("], Dark side: [Color: ").append(colorDark)
                .append(", Type: ").append(typeDark)
                .append("]");
        return sb.toString();
    }

}
