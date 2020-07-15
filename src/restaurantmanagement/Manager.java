package restaurantmanagement;

import java.util.*;


public class Manager {
    private static String shopName;
    private String name, username, password;
    private static Scanner input;
    private static ArrayList<Manager> managers = new ArrayList<>();

    public Manager(){
        this("", "", "");
    }

    public Manager(String username, String password){
        this("", username, password);
    }

    public Manager(String name, String username, String password){
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public static String getShopName() {
        return shopName;
    }

    public static void setShopName(String shopName) {
        Manager.shopName = shopName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public void createNewManager()throws Exception{  //untuk membuat data manager baru

        receiveCredentials(); //Terima input untuk data manager baru
        managers.add(this);
        Thread.sleep(2000);
        System.out.println("");
        System.out.println("< Memproses Data.... >");
        Thread.sleep(3000);
        System.out.println("< Akun Manager Berhasil Dibuat ! >"); 
        System.out.println(""); 
         
    }

    public void receiveCredentials(){ //untuk menerima input data manager
        //Membuat Data Manager
        input = new Scanner(System.in);
        System.out.println("");
        System.out.println("==============================================="); 
        System.out.println("|         [Membuat Data Manager Baru]         |");
        System.out.println("==============================================="); 
        System.out.print("Masukkan Nama Manager : ");
        name = input.nextLine();
        System.out.print("Masukkan Username baru : ");
        username = input.nextLine();
        System.out.print("Masukkan Password : ");
        String passwordFirstTyped = input.nextLine();
        System.out.print("Ketik ulang Password : ");
        String passwordRetyped = input.nextLine();
        while(!passwordFirstTyped.equals(passwordRetyped)){
            System.out.println("Password yang diketik ulang tidak cocok! Coba lagi.");
            System.out.print("Masukkan Password : ");
            passwordFirstTyped = input.nextLine();
            System.out.print("Ketik ulang Password : ");
            passwordRetyped = input.nextLine();
        }
        password = passwordFirstTyped;
    }

    public boolean isValidManager(){ //untuk memvalidasi username dan pass word manager
        boolean isValid = false;
        for (Manager manager: managers){
            if (manager.username.equals(username) && manager.password.equals(password))
                isValid = true;
        }
        return isValid; //Validasi Data Manager
    }




    public void editMenu(){  //Digunakan agar Manager bisa mengedit Menu Makanan
        String mealCode = "", mealName;
        int mealPrice;
        int choice = -1;

        mealCode = mealCodeInput(); 

        if (mealCode.equals("0"))
            return;

        
        int mealIndex = Meal.getMealCodes().indexOf(mealCode);

        while(choice != 0){
            Meal.displayMeal(mealCode);
            choice = -1;
            System.out.println("\nEDIT MENU (Pilih salah satu bagian yang ingin diedit)\n---------");
            System.out.println("1.Edit Kode Makanan / Minuman \n2.Edit Nama Makanan Minuman\n3.Edit Harga Makanan / Minuman\n-----------------");
            System.out.println("----------------------------------"); 
            while(!(choice >= 0 && choice <= 3)){
                System.out.print("Input menu (0 untuk Keluar): ");
                String choiceS = input.nextLine();
                try{
                    choice = Integer.valueOf(choiceS);
                }
                catch(NumberFormatException ex){
                    System.out.println("Input tidak valid !");
                }
                if (!(choice >= 0 && choice <= 3))
                    System.out.println("Pilihan yang dimasukkan salah! Coba lagi.");
            } 

            if (choice == 0)
                return; 

            if (choice == 1){
                
                System.out.print("Masukkan Kode Makanan / Mimuman Baru : ");
                mealCode = input.nextLine();
                while(containsString(mealCode, Meal.getMealCodes()));{
                    System.out.println("Kode makan yang dimasukkan sudah ada! Coba lagi.");
                    System.out.print("Masukkan Kode Makanan / Mimuman Baru : ");
                    mealCode = input.nextLine();
                }
                // Tetapkan kode makanan baru, ambil nama dan harga dalam indeks makanan yang sama dan hapus dari indeks makanan
                Meal.getMealCodes().set(mealIndex, mealCode);
                mealName = Meal.getMealNames().get(mealIndex);
                mealPrice = Meal.getMealPrices().get(mealIndex);
                Meal.getMealNames().remove(mealIndex);
                Meal.getMealPrices().remove(mealIndex);
                // Urutkan kode makanan untuk memposisikan ulang kode makanan baru
                Collections.sort(Meal.getMealCodes());
                mealIndex = Meal.getMealCodes().indexOf(mealCode);
                // Tambahkan nama dan harga pada indeks makanan yang sesuai dengan kode makanan
                Meal.getMealNames().add(mealIndex, mealName);
                Meal.getMealPrices().add(mealIndex, mealPrice);
                System.out.println("Kode Makanan / Minuman berhasil diubah.");
            }
            else if (choice == 2){

                System.out.print("Masukkan nama makanan baru: ");
                mealName = input.nextLine();
                Meal.getMealNames().set(mealIndex, mealName);
                System.out.println("Nama Makanan / Minuman berhasil diubah.");
            }
            else if (choice == 3){
                
                mealPrice = mealPriceInput();
                Meal.getMealPrices().set(mealIndex, mealPrice);
                System.out.println("Harga Makanan / Minuman berhasil diubah.");
            }
        }
    }

    public void addMenu(){ //untuk menambahkan menu baru dari manager
        String mealName, mealCode = "";
        int mealPrice = -1;
        int mealIndex;

        boolean isExist = true;
        while(isExist){
            System.out.print("\nMasukkan kode makanan baru (0 untuk keluar): ");
            mealCode = input.nextLine();
            if (mealCode.equals("0"))
                break;
            isExist = containsString(mealCode, Meal.getMealCodes());
            if(isExist)
                System.out.println("Kode Makanan Sudah Ada");
        } 

        if (mealCode.equals("0"))
            return; //kembali ke menu manager

        System.out.print("Masukkan Nama Makanan / Minuman Baru : ");
        mealName = input.nextLine();

        mealPrice = mealPriceInput(); // Terima harga makanan baru

        // Tambahkan dan urutkan kode makan untuk memposisikan ulang kode makanan baru
        Meal.getMealCodes().add(mealCode);
        Collections.sort(Meal.getMealCodes());
       
        mealIndex = Meal.getMealCodes().indexOf(mealCode);
        // Tambahkan nama dan harga pada indeks makanan yang sesuai dengan kode makanan
        Meal.getMealPrices().add(mealIndex, mealPrice);
        Meal.getMealNames().add(mealIndex, mealName);
        System.out.println("Menu Sudah Ditambahkan...");
    }

    public void deleteMenu(){ // untuk menghapus menu oleh manager
        String mealCode;
        int mealIndex;

        mealCode = mealCodeInput(); //Receive new meal code
        if (mealCode.equals("0"))
            return;

        Meal.displayMeal(mealCode);
        mealIndex = Meal.getMealCodes().indexOf(mealCode);
        Meal.getMealCodes().remove(mealIndex);
        Meal.getMealNames().remove(mealIndex);
        Meal.getMealPrices().remove(mealIndex);
        System.out.println("Berhasil dihapus...");
    }


    public String mealCodeInput(){ // Untuk input code Makanan / Minuman yang mau didelete
        boolean isExist = false;
        String mealCode = "";
        while(!isExist){
            System.out.print("\nMasukkan kode makanan (0 untuk keluar): ");
            mealCode = input.nextLine();
            if (mealCode.equals("0"))
                break;
            isExist = containsString(mealCode, Meal.getMealCodes());
            if(!isExist)
                System.out.println("Makanannya tidak ada! Coba lagi.");
        } 
        return mealCode;
    }

    private boolean containsString(String testString, ArrayList<String> list){
        return list.contains(testString);
    } // Periksa apakah string yang diberikan ada dalam daftar array string

    private int mealPriceInput(){ // Input Harga Baru
        int mealPrice = -1;
        while(!(mealPrice >= 0)){
            System.out.print("Masukkan Harga Baru : Rp ");
            String mealPriceS = input.nextLine();
            try{
                mealPrice = Integer.valueOf(mealPriceS);
            }
            catch(NumberFormatException ex){
                System.out.println("Input tidak valid");
            }
            if (!(mealPrice >= 0))
                System.out.println("Harga makanan yang dimasukkan tidak valid! Coba lagi.");
        }
        return mealPrice;
    }
}
