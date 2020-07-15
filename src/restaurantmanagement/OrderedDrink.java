package restaurantmanagement;


import restaurantmanagement.Meal;

public class OrderedDrink extends Meal{
    private boolean isDrinkHot;
   

    public OrderedDrink(){
        this("", "", 0, false);
    }

    public OrderedDrink(String mealCode, String mealName, int mealPrice, boolean isTakeAway){
        super(mealCode, mealName, mealPrice, isTakeAway);
    }

    public boolean isDrinkHot() {
        return isDrinkHot;
    }

    public void setIsDrinkHot(boolean isDrinkHot) {
        this.isDrinkHot = isDrinkHot;
    }



    public String drinkHotness(){
        if (isDrinkHot)
            return "Panas";
        else
            return "Dingin";
    } // apakah minuman panas / dingin



    public String toString(){
        // Mengembalikan informasi pesanan minuman
        String mealDetails = "-" + mealOrderType() + "---" + drinkHotness() ;
        return String.format("%-10s%-25s%-65s", getMealCode(), getMealName(), mealDetails) + getMealStatus() + remarks() + "\n";
    }

    public int mealPrice(){
      // Metode abstrak dalam superclass diterapkan untuk mengembalikan harga makanan
        return getMealPrice();
    }
}
