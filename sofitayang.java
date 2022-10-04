import java.io.*;
import java.util.*;
import java.util.StringTokenizer;

public class sofitayang {
    private static InputReader in;
    private static PrintWriter out;
    private static ArrayList<Menu> arrayMenu = new ArrayList<Menu>();;
    private static PriorityQueue<Koki> arrayKoki = new PriorityQueue<Koki>();;
    private static PriorityQueue<Koki> arrayKokiS = new PriorityQueue<Koki>();;
    private static PriorityQueue<Koki> arrayKokiG = new PriorityQueue<Koki>();;
    private static PriorityQueue<Koki> arrayKokiA = new PriorityQueue<Koki>();;
    private static ArrayList<Pelanggan> arrayPelanggan = new ArrayList<Pelanggan>();;

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        int jumlahMenu = in.nextInt();

        for (int i = 0; i < jumlahMenu; i++) {
            int hargaMenu = in.nextInt();
            char jenisMenu = in.next().charAt(0);
            Menu objMenu = new Menu(jenisMenu, hargaMenu);
            arrayMenu.add(objMenu);
        }

        int jumlahKoki = in.nextInt();

        for (int i = 0; i < jumlahKoki; i++) {
            char spesialis = in.next().charAt(0);
            Koki objKoki = new Koki(spesialis);
            arrayKoki.add(objKoki);
        }

        int jumlahPelanggan = in.nextInt();
        int jumlahKursi = in.nextInt();
        int jumlahHari = in.nextInt();

        for(int i = 0; i < jumlahHari; i++){
            int jumlahPelangganHariIni = in.nextInt();
            for(int j = 0; j < jumlahPelangganHariIni; j++){
                int idPelanggan = in.nextInt();
                char statusKesehatan = in.next().charAt(0);
                int uangPelanggan = in.nextInt();
                Pelanggan objPelanggan = new Pelanggan(idPelanggan, statusKesehatan, uangPelanggan);
                arrayPelanggan.add(objPelanggan);
                out.println(arrayPelanggan.get(j).getId() + " " + arrayPelanggan.get(j).getKesehatan() + " " + arrayPelanggan.get(j).getUang());
            }
            int jumlahLayanan = in.nextInt();
            for(int k = 0; k < jumlahLayanan; k++){
                char jenisLayanan = in.next().charAt(0);
                if(jenisLayanan == 'P'){
                    int idPelanggan = in.nextInt();
                    int indexMakanan = in.nextInt();
                    int indexKoki = layananP(idPelanggan, indexMakanan);
                    out.println(indexKoki);
                }
                else if(jenisLayanan == 'L'){
                    int indexPelanggan = layananL();
                    out.print(indexPelanggan);
                }
                else if(jenisLayanan == 'B'){
                    int idPelanggan = in.nextInt();
                    int statusBayar = layananB(idPelanggan);
                    out.println(statusBayar);
                }
                else if(jenisLayanan == 'C'){
                    int jumlahLihatKoki = in.nextInt();
                    for (int l = 0; l < jumlahLihatKoki; l++) {
                        int statusKoki = layananC();
                        out.println(statusKoki);
                    }
                }
                else if(jenisLayanan == 'D'){
                    int costA = in.nextInt();
                    int costG = in.nextInt();
                    int costS = in.nextInt();
                    int paketMurah = layananD(costA, costG, costS);
                    out.println(paketMurah);
                }
            }

        }
        out.close();
    }

    // taken from https://codeforces.com/submissions/Petr
    // together with PrintWriter, these input-output (IO) is much faster than the
    // usual Scanner(System.in) and System.out
    // please use these classes to avoid your fast algorithm gets Time Limit
    // Exceeded caused by slow input-output (IO)
    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }
    public static int layananP(int idPelanggan, int indexMakanan) {

        return -1;
    }
    public static int layananL() {

        return -1;
    }
    public static int layananB(int idPelanggan) {
        return-1;
    }
    public static int layananC() {
        return -1;
    }
    public static int layananD(int costA, int costG, int costS) {
        return -1;
    }
}

class Menu {
    private char jenis;
    private int harga;

    public Menu(char jenis, int harga) {
        this.jenis = jenis;
        this.harga = harga;
    }

    public char getJenis() {
        return jenis;
    }

    public int getHarga() {
        return harga;
    }
}

class Pelanggan {
    private int id;
    private char kesehatan;
    private int uang;
    private boolean blacklist;

    public Pelanggan(int id, char kesehatan, int uang) {
        this.id = id;
        this.kesehatan = kesehatan;
        this.uang = uang;
        this.blacklist = false;
    }

    public int getId() {
        return id;
    }

    public char getKesehatan() {
        return kesehatan;
    }

    public int getUang() {
        return uang;
    }

    public boolean getBlacklist() {
        return blacklist;
    }

    public void setKesehatan(char kesehatan) {
        this.kesehatan = kesehatan;
    }

    public void setUang(int uang) {
        this.uang = uang;
    }

    public void setBlacklist(boolean blacklist) {
        this.blacklist = blacklist;
    }
}

class Koki implements Comparable<Koki> {
    private int id;
    private char spesialis;
    private int pelayanan;
    private static int idAll = 1;

    public Koki(char spesialis) {
        this.id = idAll++;
        this.spesialis = spesialis;
        this.pelayanan = 0;
    }

    public int getId() {
        return id;
    }

    public char getSpesialis() {
        return spesialis;
    }

    public int getPelayanan() {
        return pelayanan;
    }

    public void setPelayanan(int pelayanan) {
        this.pelayanan = pelayanan;
    }

    @Override
    public int compareTo(Koki that) {
        // TODO Auto-generated method stub
        if(this.pelayanan < that.pelayanan){
            return 1;
        }
        else if(this.pelayanan == that.pelayanan  && this.id < that.id){
            return 0;
        }
        else{
            return -1;
        }
    }
    
}
