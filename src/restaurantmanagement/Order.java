package restaurantmanagement;


import java.util.*;
import java.text.*;

public class Order{
    private int noOfPax;
    private Date orderTakenTime;
    private ArrayList<Meal> meals = new ArrayList<>();
    private Scanner input;
    private static final int PPN = 10;

    public Order(){
        this(0);
    }

    public Order(int noOfPax){
        this.noOfPax = noOfPax;
        orderTakenTime = new Date();
    }

    public int getNoOfPax() {
        return noOfPax;
    }

    public void setNoOfPax(int noOfPax) {
        this.noOfPax = noOfPax;
    }

    public ArrayList<Meal> getMeals() {
        return meals;
    }

    public void setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
    }

    public Date getOrderTakenTime() {
        return orderTakenTime;
    }

    public void setOrderTakenTime(Date orderTakenTime) {
        this.orderTakenTime = orderTakenTime;
    }

    public void addMeal(int tableNum){ //menambahkan orderan
        input = new Scanner(System.in);
        String mealCode = "";
        char answer;

        while(!mealCode.equals("0")){
            boolean isTakeAway = false;
            boolean codeExist = false;
            while(!codeExist){
                System.out.print("Masukkan kode meal (0 untuk keluar): ");
                mealCode = input.nextLine();
                if (mealCode.equals("0"))
                    break;
                codeExist = Meal.getMealCodes().contains(mealCode);
                if (!codeExist)
                    System.out.println("Maaf kode meal tidak sesuai!");
            } //Periksa apakah kode meal yang dimasukkan ada di sistem
            if (mealCode.equals("0"))
                continue; //kembali ke status table

            int indexOfMeal = Meal.getMealCodes().indexOf(mealCode); //Mengambil indeks kode makan
            System.out.println(Meal.getMealNames().get(indexOfMeal)); //Menampilkan nama makanan setelah menerima kode makanan

            answer = ' ';
            while(!(answer == 'N' || answer == 'Y')){
                System.out.print("Take away?\nTekan 'Y' untuk yes dan 'N' untuk no: ");
                String answerS = input.nextLine();
                if (answerS.length() >= 1)
                    answer = Character.toUpperCase(answerS.charAt(0));
                if (answer == 'Y')
                    isTakeAway = true;
                if (!(answer == 'N' || answer == 'Y'))
                    System.out.println("Inputan tidak valid");
            } //Validasi opsi apakah makanan sudah dine in atau dibawa pulang

            Meal meal;
            if (mealCode.contains("DR")){
                // Membuat objek OrderedDrink jika makanannya adalah minuman
                meal = new OrderedDrink(mealCode, Meal.getMealNames().get(indexOfMeal), Meal.getMealPrices().get(indexOfMeal), isTakeAway);
                addDrinkInfo(meal);
            }
            else{
                // Membuat objek OrderedFood jika makanannya adalah makanan
                meal = new OrderedFood(mealCode, Meal.getMealNames().get(indexOfMeal), Meal.getMealPrices().get(indexOfMeal), isTakeAway);
                addFoodInfo(meal, isTakeAway);
            }

            System.out.print("Masukkan special request : ");
            String remarks = input.nextLine();
            meal.setRemarks(remarks); //menulis keterangan

            meals.add(meal); //Tambahkan makanan untuk memesan daftar makanan
            Table.getTables().get(tableNum-1).setStatus("OT");
            Table.getTables().get(tableNum-1).setOrder(this);
            System.out.println("Makanan / Minuman Sudah Ditambah ke List Orderan>>");
        }
    }

    public void addDrinkInfo(Meal meal){ //menambahkan informasi minuman pada orderan
       
        char answer = ' ';
        boolean isDrinkHot = false;

        while (!(answer == 'N' || answer == 'Y')){
            
            System.out.print("Apakah Minumannya Panas ?\nTekan 'Y' for yes dan 'N' untuk no: ");
            String answerS = input.nextLine();
            if (answerS.length() >= 1)
                answer = Character.toUpperCase(answerS.charAt(0));
            if (answer == 'Y')
                isDrinkHot = true;
            if (!(answer == 'N' || answer == 'Y'))
                System.out.println("Input tidak valid!");

        } // Memvalidasi input untuk minuman panas dan ukuran minuman

        ((OrderedDrink)meal).setIsDrinkHot(isDrinkHot);
        
    }

    public void addFoodInfo(Meal meal, boolean isTakeAway){ //menambahkan informasi makanan pada orderan
        String drinkCode = "";
        char answer = ' ';

        while(!(answer == 'N' || answer == 'Y')){
            // Menerima input apakah makanan dengan minuman atau tidak
            System.out.print("Apakah ini minuman ?\nTekan 'Y' untuk yes dan 'N' untuk no: ");
            String answerS = input.nextLine();
            if (answerS.length() >= 1)
                answer = Character.toUpperCase(answerS.charAt(0));
            if (!(answer == 'N' || answer == 'Y'))
                System.out.println("Inputan tidak valid");
        }
        if (answer == 'Y'){ // Jika makanan disertai minuman, dapatkan informasi minuman
            boolean codeExist = false;

            while(!codeExist){
                System.out.print("Masukkan Kode Minuman : ");
                drinkCode = input.nextLine();
                if (drinkCode.contains("DR"))
                    codeExist = Meal.getMealCodes().contains(drinkCode);
                if (!codeExist)
                    System.out.println("Kode Minuman Tidak Valid");
            } 

            int indexOfDrink = Meal.getMealCodes().indexOf(drinkCode); // Mengambil indeks umum informasi minuman
            System.out.println(Meal.getMealNames().get(indexOfDrink));
            Meal drink = new OrderedDrink(drinkCode, Meal.getMealNames().get(indexOfDrink), Meal.getMealPrices().get(indexOfDrink), isTakeAway);
            addDrinkInfo(drink); // Membuat minuman  dan menambahkan informasi untuk minuman
            ((OrderedFood)meal).setSetDrink((OrderedDrink)drink); 
        }
    }

    public void removeMeal(int tableNum){ //menghapus makanan dari orderan
        int mealNo = -1;
        ArrayList<Meal> meals = Table.getTables().get(tableNum-1).getOrder().getMeals();
        while(!(mealNo >= 0 && mealNo <= meals.size())){
            displayOrder(tableNum);
            System.out.print("Masukkan nomor makanan dalam daftar pesanan yang ingin dihapus(0 untuk keluar): ");
            String mealNoS = input.nextLine();
            try{
                mealNo = Integer.valueOf(mealNoS);
            }
            catch(NumberFormatException ex){
                System.out.println("Input tidak valid terdeteksi!");
            }
            if (!(mealNo >= 0 && mealNo <= meals.size()))
                System.out.println("Inputan valid");
        } // Terima input untuk posisi makanan dalam daftar pesanan dan validasikan input posisi makan

        if (mealNo == 0){
            return;
        } // Jika mealNo adalah 0, maka keluar dari fungsi

        meals.remove(mealNo-1);
        System.out.println("Makanan berhasil dihapus dari daftar pesanan."); // Hapus makanan tertentu dari daftar pesanan
    }

    public void displayOrder(int tableNum){ //menampilkan data manager pada orderan
        Order order = Table.getTables().get(tableNum-1).getOrder();

        if(order!=null){
           // Header tabel untuk pencetakan daftar pesanan
            System.out.print("\nNo. Kode      Nama                     Detail                                                           Status   \n");
            System.out.println("--- --------- ------------------------ ---------------------------------------------------------------- -----------");
            int count = 1;
            for (Meal meal: order.getMeals()){
                System.out.printf("%-4d%s", count, meal.toString());
                count++;
            }
        } // Jika ada makanan dalam daftar pesanan, maka cetak informasi makanan
        else
            System.out.println("Tidak Ada Orderan");
    }

    public void deleteOrder(int tableNum){ //menghpus data orderan
        char answer = ' ';
        while(!(answer == 'Y' || answer == 'N')){
            System.out.print("Anda yakin ingin menghapus seluruh pesanan?\nTekan 'Y' untuk yes dan 'N' untuk no: ");
            String answerS = input.nextLine();
            if (answerS.length() >= 1)
                answer = Character.toUpperCase(answerS.charAt(0));
            if (!(answer == 'Y' || answer == 'N'))
                System.out.println("Input tidak valid terdeteksi! Coba lagi.");
        } 

        if (answer == 'Y'){
            Table.getTables().get(tableNum-1).setOrder(null);
            Table.getTables().get(tableNum-1).setStatus("NO");
            System.out.println("Pesanan berhasil dihapus");
        } 
    }

     public void printReceipt(int tableNum){ //menampilkan struk
        
        boolean haveDineIn = false;
        boolean haveTakeAway = false;
        
        int total = 0, subTotal = 0;
        int ppn = 0;

        Order order = Table.getTables().get(tableNum-1).getOrder();
        

        
        System.out.println("\n" + Manager.getShopName()+"\n");
        System.out.println("-----------------PRINT RECEIPT-------------------");
        System.out.println("-------------------------------------------------");

       
        SimpleDateFormat ft = new SimpleDateFormat ("E dd.MM.yyyy  hh:mm:ss a");
        System.out.println("\n" + ft.format(order.getOrderTakenTime()));
        
        System.out.println("\nMEJA NO: " + tableNum);
        System.out.println();

        ArrayList<Meal> takeAwayMeals = new ArrayList<>();
        ArrayList<Meal> dineInMeals = new ArrayList<>();

        
        for(Meal meal: meals){
            subTotal += meal.mealPrice();
            if(meal.getIsTakeAway()){
                haveTakeAway = true;
                takeAwayMeals.add(meal);
            }
            else{
                haveDineIn = true;
                dineInMeals.add(meal);
            }
        }
        
        if(haveDineIn){
            System.out.println("<Dine In>");
            receiptMealDisplay(dineInMeals);
        }
        if(haveTakeAway){
            System.out.println();
            System.out.println("<Take Away>");
            receiptMealDisplay(takeAwayMeals);
        }

        total = subTotal ; 
        ppn = subTotal*PPN/100;
        total += ppn; 
        
  
        System.out.println("\n\nSub Total(Rp):\t\t\t"+subTotal);
        System.out.println("PPN (10%)(Rp):\t\t\t"+ppn);
        System.out.println("Total(Rp):\t\t\t"+total);


        
        System.out.println("\nCLOSED: " + ft.format(new Date()));
        System.out.println("-------------------------------------------------");
        System.out.println("-------------------------------------------------");
        System.out.println("");
        System.out.println("                Terima Kasih !!");
        System.out.println("            Jangan Lupa Datang Kembali");
        System.out.println("");
        System.out.println("-------------------------------------------------");

        Table.getTables().get(tableNum-1).setStatus("NO");
    }

    public void receiptMealDisplay(ArrayList<Meal> meals){  //menampilkan makanan" apa aja yang dipesan untuk ditampilkan dalam struk
        
        for (Meal meal: meals){
       
            System.out.print(meal.getMealCode()+"\t"+meal.getMealName()+"\t"+meal.mealPrice());
        }
    }

    public static void displayTableOrders(ArrayList<Table> tables){  //menampilkan orderan meja
        tables.clear();
        // Mencari tabel dengan urutan dan menambahkannya ke daftar tabel
        for (Table table: Table.getTables()){
            if (table.getStatus() == "OT")
                tables.add(table);
        }
        if(tables.isEmpty())
            return;// Kembali ke menu akses sistem
       // Menyortir tabel sesuai dengan waktu pengambilan pesanan
        Collections.sort(tables);
        // Tampilkan pesanan dengan nomor meja sesuai dengan pesanan yang datang terlebih dahulu
        System.out.println("\nSemua Order\n----------");
        for (Table table: tables){
            System.out.print("\nNomor Meja " + table.getTableNum());
            table.getOrder().displayOrder(table.getTableNum());
        }
    }

    public static void setOrderReady(Table chosenTable){ //set order telah ready
        boolean isOrderReady = true;
       
        for(Meal meal: chosenTable.getOrder().getMeals()){
            if(meal.getMealStatus().equals("Cooking")){
                isOrderReady = false;
                break;
            }
        }
        if (isOrderReady)
            chosenTable.setStatus("FR");
    }

    public void setOrderServed(int tableNum){ //set order sudah disajikan
        char option = ' ';
        while (!(option == 'N' || option == 'Y')){
            System.out.print("\nSiap untuk Disajikan ?\nTekan 'Y' untuk yes dan 'N' untuk no: ");
            String optionS = input.nextLine();
            if (optionS.length() >= 1)
                option = Character.toUpperCase(optionS.charAt(0));
            if (option == 'Y'){
                Table.getTables().get(tableNum-1).setStatus("FS");
                ArrayList<Meal> meals = Table.getTables().get(tableNum-1).getOrder().getMeals();
                for (Meal meal: meals){
                    meal.setMealStatus("Disajikan");
                }
                System.out.println("Telah Disajikan");
            }
            if (!(option == 'N' || option == 'Y'))
                System.out.println("Inputan tidak valid !");
        } 
    }
}
