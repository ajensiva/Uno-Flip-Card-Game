
public class Card {

    enum TypeLight{ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, NINE, TEN, WILDTWO, SKIP, REVERSE, DRAW_TWO, WILD_DRAW_FOUR, FLIP}

    enum TypeDark{ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, NINE, TEN, WILDTWO, SKIP, REVERSE, DRAW_TWO, WILD_DRAW_FOUR, FLIP}

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

    Card(ColorLight colorLight, TypeLight typeLight, ColorDark colorDark, TypeDark typeDark) {

        this.colorLight = colorLight;
        this.typeLight = typeLight;
        this.colorDark = colorDark;
        this.typeDark = typeDark;
    }

    public ColorLight getColorLight() {
        return this.colorLight;
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



 //new
}

// new changes karki
//new changes ajen