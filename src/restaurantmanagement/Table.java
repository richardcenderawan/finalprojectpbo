package restaurantmanagement;

import java.util.*;

public class Table implements Comparable<Table>{
    private final int tableNum;
    private String status;
    private Order order;
    private static int tablesPerRow;
    private static int totalTables;
    private static ArrayList<Table> tables = new ArrayList<>();
    private static Scanner input;

    public Table() {
        this(0, "NO");
    }

    public Table(int tableNum, String status) {
        this.tableNum = tableNum;
        this.status = status;
    }

    public int getTableNum() {
        return tableNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static int getTablesPerRow() {
        return tablesPerRow;
    }

    public static void setTablesPerRow(int tablesPerRow) {
        Table.tablesPerRow = tablesPerRow;
    }

    public static int getTotalTables() {
        return totalTables;
    }

    public static void setTotalTables(int totalTables) {
        Table.totalTables = totalTables;
    }

    public static ArrayList<Table> getTables() {
        return tables;
    }

    public static void setTables(ArrayList<Table> tables) {
        Table.tables = tables;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public static void displayTable(ArrayList<Table> tables) { //untuk menampilkan animasi meja dengan nomor meja dan status meja
        System.out.println("==============================================="); 
        System.out.println("|              [STATUS MEJA]                  |");
        System.out.println("==============================================="); 
        System.out.println("| Ket :- NO (Meja Kosong)                     |"); 
        System.out.println("|      - OT (Meja Diisi dan pesanan diinput)  |"); 
        System.out.println("|      - FR (Pesanan siap untuk diantarkan)   |");
        System.out.println("|      - FS (Pesanan telah selesai disajikan) |");
        System.out.println("==============================================="); 
        int count = 0; //To count number of tables printed

        for (int i=0; i<tables.size(); i+=tablesPerRow){ //Looping untuk mencetak setiap baris table
            int statusCount = count; //Menyimpan nilai hitungan ke statusCount sebelum memanipulasi hitungan

            for (; count<i+tablesPerRow && count<tables.size(); count++) //Cetak jumlah table masing2 di setiap baris
                System.out.printf("| %02d |", tables.get(count).tableNum);
            System.out.println();

            for (; statusCount<i+tablesPerRow && statusCount<tables.size(); statusCount++) //Cetak status table
                System.out.print("|<" + tables.get(statusCount).status + ">|");
            System.out.println();

        }
    }

    public static void configTable(){ //untuk konfigurasi meja
        System.out.println("==============================================="); 
        System.out.println("|     [Konfigurasi Meja Restoran / Kafe]      |");
        System.out.println("===============================================");
        int numOfTables = 0;
        int numPerRow = 0;
        input = new Scanner(System.in);

        while(!(numOfTables>=1 && numOfTables<=99 && numPerRow<=numOfTables && numPerRow>=1)){
            System.out.print("Masukkan jumlah Meja : ");
            String totTables = input.nextLine();
            System.out.print("Masukkan jumlah Meja per baris : ");
            String perRow = input.nextLine();
            try {
                numOfTables = Integer.valueOf(totTables);
                numPerRow = Integer.valueOf(perRow);
            }
            catch(NumberFormatException ex){
                System.out.println("Maaf Pilihan Anda tidak ada !");
            }
            if (!(numOfTables>=1 && numOfTables<=99 && numPerRow<=numOfTables && numPerRow>=1))
                System.out.println("Input tidak valid untuk total tabel atau tabel per baris!");
            else{
                Table.totalTables = numOfTables;
                Table.tablesPerRow = numPerRow; //Tetapkan jumlah table dan table per baris (untuk pencetakan)
            }
        } // Validasi konfigurasi tabel

        createTables(); // Buat jumlah objek table yang ditentukan
        System.out.println("==============================================="); 
        System.out.println("|        Meja Berhasil Dikonfigurasi !!       |");
        System.out.println("===============================================");
        System.out.println("");
    }

    public static void createTables(){
        tables.clear();
        for (int i=1; i<=totalTables; i++){
            Table table = new Table(i, "NO"); //Buat objek tabel dengan nomor tabel dan status awal "Not Occupied (NO ) / Tidak Didudukiss"
            tables.add(table);
        }
    }

    @Override
    public int compareTo(Table table){
        return order.getOrderTakenTime().compareTo(table.order.getOrderTakenTime());
    } 
}
