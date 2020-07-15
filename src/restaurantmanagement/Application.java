package restaurantmanagement;

import java.util.*;
public class Application {
    private static Scanner input;

    public static void main(String[] args) throws Exception{
        input = new Scanner(System.in);
        System.out.println("===============================================");
        System.out.println("|             SELAMAT DATANG DI               |");
        System.out.println("|       RESTAURANT MANAGEMENT SYSTEM !        |");
        System.out.println("===============================================");
        Thread.sleep(1000);
        System.out.println("< Memuat.... >");
        Thread.sleep(2000);
        System.out.println("");
        System.out.println("==============================================="); 
        System.out.println("| [Silahkan Masukkan Nama Restoran/Kafe Anda] |");
        System.out.println("===============================================");
        System.out.print("Input : ");
        String shopName = input.nextLine();
        Manager.setShopName(shopName);

        Manager manager = new Manager();
        manager.createNewManager(); //Buat akun manager baru di awal, setiap kali program dijalankan

        Table.configTable(); //Konfigurasikan tabel saat mulai, setiap kali program dijalankan

        String username = "", password = "";
        while (true){
            boolean isValid = false;
            while (!isValid){
                Thread.sleep(2000);
                System.out.println("");
                System.out.println("Hai Manager dari " + Manager.getShopName() + " ! , \nSilahkan Login :) ");
                System.out.println("");
                System.out.println("==============================================="); 
                System.out.println("|                [L O G I N]                  |");
                System.out.println("===============================================");
                System.out.print("Username : ");
                username = input.nextLine();
                System.out.print("Password : ");
                password = input.nextLine();

                manager = new Manager(username, password);
                isValid = manager.isValidManager();

                if (!isValid)
                    System.out.println("Username atau Password terdapat kesalahan ! Coba lagi...\n");
            } //Validasi dan verifikasi manager
            enterMenu(); //Masuk ke menu setelah validasi manager
        }
    }

    public static void enterMenu(){
        char access = ' ';
        while(access!='4'){
            printSystemAccess(); //Cetak printSystemAccess() dan opsi keluar 

            while (!(access>='1' && access<='4')){
                System.out.print("Pilih Sistem Akses : ");
                String systemAccess = input.nextLine();
                if (systemAccess.length() >= 1 && !(access>='1' && access<='4'))
                    access = systemAccess.charAt(0);
                if (!(access>='1' && access<='4'))
                    System.out.println("Maaf Pilihan Anda tidak ada ! Coba lagi.");
            } //Validasi opsi pengguna

            enterFunctions(access); // Masukkan akses sistem yang dipilih

            if (access!='4')
                access = ' '; //Reset ulang nilai akses untuk mengulangi opsi menu
        }
        System.out.println(Manager.getShopName() + " Berhasil Keluar.");
    }

    public static void printSystemAccess(){
        System.out.println("");
        System.out.println("==============================================="); 
        System.out.println("|               [M][E][N][U]                  |");
        System.out.println("|---------------------------------------------|"); 
        System.out.println("|                                             |");
        System.out.println("| 1.Status Table / Meja                       |");
        System.out.println("| 2.Status Makanan / Minuman                  |");
        System.out.println("| 3.Pengaturan Menu Makanan - (Khusus Manager)|");
        System.out.println("| 4.Keluar                                    |");
        System.out.println("|                                             |");
        System.out.println("|     (c)2020 Grup Cincai 19TI2 & 17TI1       |");
        System.out.println("|             All Rights Reserved             |");
        System.out.println("===============================================");  
    }

    public static void enterFunctions(char access){

        if (access=='1'){
            int tableNum = -1;

            while (tableNum != 0){
                System.out.println();
                Table.displayTable(Table.getTables()); //Menampilkan status sementara Table
                ArrayList<Integer> options = new ArrayList<>();
                while(!(tableNum >= 0 && tableNum <= Table.getTotalTables())){
                    System.out.print("Masukkan Nomor Meja (0 untuk keluar) : ");
                    String tableNumS = input.nextLine();
                    try {
                        tableNum = Integer.valueOf(tableNumS);
                    }
                    catch(NumberFormatException ex){
                        System.out.println("Input tidak valid terdeteksi! ");
                    }
                    if (!(tableNum >= 0 && tableNum <= Table.getTotalTables()))
                        System.out.println("Nomor Meja tidak tersedia !");
                } //validasi input table
                
                if (tableNum == 0) //Jika input 0 maka keluar ke system access menu
                    break;

                String status = Table.getTables().get(tableNum-1).getStatus(); //Get Status dari Table

                if (status == "NO"){
                    int noOfPax = 0;
                    System.out.println("==============================================="); 
                    System.out.println("|       [Konfigurasi Orderan Pelanggan]       |");
                    System.out.println("===============================================");

                    while(!(noOfPax >= 1)){
                        System.out.print("Masukkan No Customer : ");
                        String noOfPaxS = input.nextLine();
                        try {
                            noOfPax = Integer.valueOf(noOfPaxS);
                        }
                        catch(NumberFormatException ex){
                            System.out.println("Input tidak valid terdeteksi!");
                        }
                        if (noOfPax < 1)
                            System.out.println("Masukan untuk jumlah pelanggan tidak valid!");
                    } //validasi nomor kode customer

                    Order order = new Order(noOfPax); //membuat orderan baru
                    order.addMeal(tableNum); //Masukan meal ke order
                }
                else if (status == "OT"){
                    //Jika status table "OT", lima opsi diberikan
                    options.clear();
                    for (int i=0; i<5; i++)
                        options.add(i);
                    System.out.println();
                    int option = menuEntry(options); 
                    enterFunctions(option, tableNum); 
                }
                else if (status == "FR"){
                    // Jika status tabel adalah "FR", maka tiga opsi diberikan
                    options.clear();
                    options.add(0);
                    options.add(2);
                    options.add(4);
                    System.out.println();
                    int option = menuEntry(options); 
                    enterFunctions(option, tableNum); 
                }
                else if (status == "FS"){
                    // Jika status tabel adalah "FS", maka empat opsi diberikan
                    options.clear();
                    options.add(0);
                    options.add(2);
                    options.add(5);
                    System.out.println();
                    int option = menuEntry(options); 
                    enterFunctions(option, tableNum);
                }

                tableNum = -1; 
            } 
        }

        else if(access=='2'){
            int tableNum = -1;
            boolean isExist;
            Table chosenTable = null;
           // Membuat daftar array tabel untuk memuat tabel dengan urutan
            ArrayList<Table> tables = new ArrayList<>();

            while(tableNum != 0){
                tableNum = -1;
                Order.displayTableOrders(tables); // Tampilkan semua pesanan table

                if (tables.isEmpty()){
                    System.out.println("Tidak ada pesanan saat ini.");
                    break;
                }
                isExist = false;
                while(!(tableNum >= 0 && isExist)){
                    System.out.print("\nMasukkan nomor table (0 untuk keluar): ");
                    String tableNumS = input.nextLine();
                    try {
                        tableNum = Integer.valueOf(tableNumS);
                    }
                    catch(NumberFormatException ex){
                        System.out.println("Inputan tidak valid");
                    }
                    if (tableNum == 0)
                        break;
                    for (Table table: tables){
                        if (table.getTableNum() == tableNum){
                            isExist = true;
                            chosenTable = table; // Tetapkan chosenTable dengan tabel yang cocok dengan tableNum
                            break;
                        }
                    }
                    if (!(tableNum >= 0 && isExist))
                        System.out.println("Nomor table yang dimasukkan tidak ada dalam daftar pesanan!");
                } // Validasi input nomor table

                if (tableNum == 0)
                    continue; // Kembali ke menu akses sistem

                // Mendapatkan total jumlah makanan yang ada dalam urutan tabel
                int numOfOrders = chosenTable.getOrder().getMeals().size();
                int orderNo = 0;
                while(!(orderNo >= 1 && orderNo <= numOfOrders)){
                    System.out.print("Masukkan nomor makanan / minuman yang telah ready: ");
                    String orderNoS = input.nextLine();
                    try{
                        orderNo = Integer.valueOf(orderNoS);
                    }
                    catch(NumberFormatException ex){
                        System.out.println("Inputan Tidak Valid");
                    }
                    if (!(orderNo >= 1 && orderNo <= numOfOrders))
                        System.out.println("Nomor pesanan yang dimasukkan tidak valid! Coba lagi.");
                } // Menerima nomor pesanan dari pesanan dalam daftar nomor tabel yang dipilih

                // Membuat objek Meal untuk menahan makanan yang dipilih
                Meal chosenMeal = chosenTable.getOrder().getMeals().get(orderNo-1);
                // Mendapatkan apakah makanan yang dipilih dalam daftar pesanan sudah dimasak
                boolean isMealCooking = chosenMeal.getMealStatus().equals("Cooking");
                // Atur makanan yang dipilih sebagai Ready
                if (isMealCooking)
                    chosenMeal.setMealStatus("Ready");
                else
                    System.out.println("Makanan / Minuman yang dipilih sudah dimasak!");
                Order.setOrderReady(chosenTable); //Jika semua makanan sudah siap, tetapkan pesanan sebagai "Ready"
            }
        }

        else if (access=='3'){
            Manager manager = null;
            String username = "", password = "";

            while (!username.equals("0")){
                boolean isValid = false;
                while (!isValid){
                System.out.println("");
                System.out.println("Hai Manager dari " + Manager.getShopName() + " ! , \nSilahkan Login:) ");
                System.out.println("");
                System.out.println("==============================================="); 
                System.out.println("|                [L O G I N]                  |");
                System.out.println("===============================================");
                    System.out.print("\nUsername ('0' untuk keluar): ");
                    username = input.nextLine();
                    if (username.equals("0"))
                        break;
                    System.out.print("Password: ");
                    password = input.nextLine();

                    manager = new Manager(username, password);
                    isValid = manager.isValidManager();

                    if (!isValid)
                        System.out.println("Nama pengguna atau kata sandi salah! Coba lagi.\n");
                } // Validasi dan verifikasi manajer

                if (username.equals("0"))
                    continue; // Keluar dari akses sistem manajer segera dan kembali untuk memilih akses sistem

                char option = ' ';
                while (option != '0'){
                    displayManagerMenu(); // Menu fungsi manajer ditampilkan
                    option = ' ';
                    while (!(option >= '0' && option <= '8')){
                        System.out.print("Masukkan opsi menu (0 untuk keluar): ");
                        String optionS = input.nextLine();
                        if (optionS.length() >= 1)
                            option = optionS.charAt(0);
                        if (!(option >= '0' && option <= '8'))
                            System.out.println("Opsi tidak valid! Coba lagi.");
                    } //Validasi manager option

                    if (option == '0')
                        enterMenu(); //Jika input 0, maka kembali ke manager login
                    else if (option == '1')
                        manager.editMenu();
                    else if (option == '2')
                        manager.addMenu();
                    else if (option == '3')
                        manager.deleteMenu();
                
                 
                }
            }
        } else if (access=='4'){exit();}
    }

    public static int menuEntry(ArrayList<Integer> options){
       
        String[] orderFunctions = {"Tambahkan item Makanan / Minuman", "Hapus item Makanan / Minuman", "Tampilkan Order", "Hapus Order", "Sajikan Makanan", "Print Receipt"};
        int functionsNo = 1;
        for (int i=0; i<orderFunctions.length; i++){
            if (options.contains(i)){
                System.out.println(functionsNo + ". " + orderFunctions[i]);
                functionsNo++;
            }
        } //Untuk menampilkan pilihan yang diizinkan sesuai dengan status tabel
        System.out.println("------------------");

        int option = 0;
        while (!(option >= 1 && option <= options.size())){
            System.out.print("Pilih : ");
            String optionS = input.nextLine();
            try{
                option = Integer.valueOf(optionS);
            }
            catch(NumberFormatException ex){
                System.out.println("Input tidak valid !");
            }
            if (!(option >= 1 && option <= options.size()))
                System.out.println("Input tidak valid !");
        } 

        option = options.get(option-1) + 1; //Pastikan opsi dipetakan ke opsi yang benar dalam daftar opsi
        return option;
    }

    public static void enterFunctions(int option, int tableNum){
        Order order = Table.getTables().get(tableNum-1).getOrder();

        if (option == 1)
            order.addMeal(tableNum);
        else if (option == 2)
            order.removeMeal(tableNum);
        else if (option == 3){
            order.displayOrder(tableNum);
            System.out.print("\nTekan 'Enter' untuk melanjutkan...");
            input.nextLine();
        }
        else if (option == 4)
            order.deleteOrder(tableNum);
        else if (option == 5)
            order.setOrderServed(tableNum);
        else if (option == 6){
            order.printReceipt(tableNum);
            System.out.print("\nTekan 'Enter' untuk melanjutkan...");
            input.nextLine();
        }
        
    }

    public static void displayManagerMenu(){
        System.out.println();
        System.out.println("==============================================="); 
        System.out.println("|              [MENU MANAGER]                 |");
        System.out.println("===============================================");
        System.out.println("|                                             |");        
        System.out.println("| 1. Edit Item Menu Makanan / Minuman         |");
        System.out.println("| 2. Tambah Item Menu Makanan / Minuman       |");
        System.out.println("| 3. Hapus Item Menu Makanan / Minuman        |");
        System.out.println("|                                             |");  
        System.out.println("===============================================");
    }

    public static void exit(){
        char option = ' ';
        System.out.println("Anda yakin ingin keluar? Semua data yang disimpan akan hilang!");
        while (!(option == 'N')){
            System.out.print("Tekan 'Y' untuk \"Yes\" dan 'N' untuk \"No\": ");
            String optionS = input.nextLine();
            if (optionS.length() >= 1)
                option = Character.toUpperCase(optionS.charAt(0));
            if (option == 'Y'){
                System.exit(0);
            } 
            if (!(option == 'N'))
                System.out.println("Inputan Tidak Valid");
        }
    }
}

