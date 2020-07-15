package restaurantmanagement;



import java.util.*;

public abstract class Meal {
    private String mealName;
    private String mealCode;
    private int mealPrice;
    private boolean isTakeAway;
    private String remarks;
    private String mealStatus;
    private static double setDrinkPrice = 0;
    private static final String[] mealCodeArray = {"DR1", "DR2", "DR3", "DR4", "DR5", "F01", "F02", "F03", "F04", "F05"};
   // Kode makan default saat program dimulai
    private static final String[] mealNameArray = {"Teh Manis", "Aqua", "Kopi", "Lemon Tea", "Jus", 
        "Nasi Goreng", "Indomie Goreng", "Indomie Goreng Kuah", "Indomie Becek", "Nasi Prang",};
   // Nama makan default pada awal program
    private static final Integer[] mealPriceArray = {5000, 4000, 10000, 10000, 12000, 13000, 12000, 12000, 12000, 5000};
    // Harga makanan standar saat program dimulai
    private static ArrayList<String> mealCodes = new ArrayList<>(Arrays.asList(mealCodeArray));
    private static ArrayList<String> mealNames = new ArrayList<>(Arrays.asList(mealNameArray));
    private static ArrayList<Integer> mealPrices = new ArrayList<>(Arrays.asList(mealPriceArray));
    // Konversi dari array ke daftar array untuk memungkinkan perubahan untuk kode, nama dan harga oleh manager

    public Meal(){
        this("", "", 0, false);
    }

    public Meal(String mealCode, String mealName, int mealPrice, boolean isTakeAway){
        this.mealName = mealName;
        this.mealCode = mealCode;
        this.mealPrice = mealPrice;
        this.isTakeAway = isTakeAway;
        remarks = "";
        mealStatus = "Cooking";
    }

    public String getMealName(){
        return mealName;
    }

    public void setMealName(String mealName){
        this.mealName = mealName;
    }


    public String getMealCode(){
        return mealCode;
    }

    public void setMealCode(String mealCode){
        this.mealCode = mealCode;
    }

    public int getMealPrice(){
        return mealPrice;
    }

    public void setMealPrice(int mealPrice){
        this.mealPrice = mealPrice;
    }

    public boolean getIsTakeAway() {
        return isTakeAway;
    }

    public void setIsTakeAway(boolean isTakeAway) {
        this.isTakeAway = isTakeAway;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getMealStatus() {
        return mealStatus;
    }

    public void setMealStatus(String mealStatus) {
        this.mealStatus = mealStatus;
    }

    public static double getSetDrinkPrice() {
        return setDrinkPrice;
    }

    public static void setSetDrinkPrice(double setDrinkPrice) {
        Meal.setDrinkPrice = setDrinkPrice;
    }

    public static ArrayList<String> getMealCodes() {
        return mealCodes;
    }

    public static void setMealCodes(ArrayList<String> mealCodes) {
        Meal.mealCodes = mealCodes;
    }

    public static ArrayList<String> getMealNames() {
        return mealNames;
    }

    public static void setMealNames(ArrayList<String> mealNames) {
        Meal.mealNames = mealNames;
    }

    public static ArrayList<Integer> getMealPrices() {
        return mealPrices;
    }

    public static void setMealPrices(ArrayList<Integer> mealPrices) {
        Meal.mealPrices = mealPrices;
    }

    public String mealOrderType(){ //menampilkan take away atau dinein
        if (getIsTakeAway())
            return "Take Away";
        else
            return "Dine-in";
    } 

    public String remarks(){ //keterangan makanan/minuman
        if (remarks.equals(""))
            return "";
        else
            return "\nRemarks: " + remarks;
    } 

    @Override
    public abstract String toString(); // Metode abstrak yang harus diimplementasikan dalam subclass

    public static void displayMeal(String mealCode){
        int mealIndex = mealCodes.indexOf(mealCode);
        System.out.println("\nKode Makanan / Minuman : " + mealCode);
        System.out.println("Nama Makanan / Minuman : " + mealNames.get(mealIndex));
        System.out.println("Harga : Rp "+mealPrices.get(mealIndex));
    }

    public abstract int mealPrice(); // Metode abstrak yang harus diimplementasikan dalam subclass
}
