
public class Card {

    enum typeLight{ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, NINE, TEN, WILDTWO, SKIP, REVERSE, DRAW_TWO, WILD_DRAW_FOUR, FLIP}

    enum ColorDark {
        Pink, Teal, Purple, Orange
    }

    enum TypeLight {
        One, Two, Three, Four
    }

    enum TypeDark {
        One, Two, Three, Four
    }


    private  Colorlight colorLight;
    private  TypeLight typeLight;
    private  ColorDark colorDark;
    private  TypeDark typeDark;

    Card(Colorlight colorLight, TypeLight typeLight, ColorDark colorDark, TypeDark typeDark) {

        this.colorLight = colorLight;
        this.typeLight = typeLight;
        this.colorDark = colorDark;
        this.typeDark = typeDark;


    }

    public Colorlight getColorLight() {
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




}
