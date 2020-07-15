package restaurantmanagement;


import restaurantmanagement.Meal;

public class OrderedFood extends Meal{
    private OrderedDrink setDrink;

    public OrderedFood(){
        this("", "", 0 , false);
    }

    public OrderedFood(String mealCode, String mealName, int mealPrice, boolean isTakeAway){
        super(mealCode, mealName, mealPrice, isTakeAway);
    }

    public OrderedDrink getSetDrink() {
        return setDrink;
    }

    public void setSetDrink(OrderedDrink setDrink) {
        this.setDrink = setDrink;
    }

    public String setDrink(){ //jika minuman , maka akan diset
        if (setDrink!=null){
            return "Set Minuman : " + setDrink.getMealCode() + "-" + setDrink.getMealName() + "---" + setDrink.drinkHotness() + "---" ;
        }
        else
            return "Tidak Set Minuman";
    }

    public String toString(){ //menampilkan detail orderan
       
        String mealDetails = "-" + mealOrderType() + "---" + setDrink();
        return String.format("%-10s%-25s%-65s", getMealCode(), getMealName(), mealDetails) + getMealStatus() + remarks() + "\n";
    }

    public int mealPrice(){//menampilkan harga
        
        if(setDrink == null)
            return getMealPrice();
        else
            return (int) (getMealPrice() + getSetDrinkPrice());
    }
}
