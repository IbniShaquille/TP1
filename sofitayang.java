import java.io.*;
import java.util.*;
import java.util.StringTokenizer;

public class sofitayang {
    private static InputReader in;
    private static PrintWriter out;
    private static ArrayList<Menu> arrayMenu = new ArrayList<Menu>();;
    private static TreeSet<Koki> arrayKoki = new TreeSet<Koki>();;
    private static TreeSet<Koki> arrayKokiS = new TreeSet<Koki>();;
    private static TreeSet<Koki> arrayKokiG = new TreeSet<Koki>();;
    private static TreeSet<Koki> arrayKokiA = new TreeSet<Koki>();;
    private static ArrayList<Koki> arrayKokiLayanan = new ArrayList<Koki>();;
    private static ArrayList<Pelanggan> arrayPelanggan = new ArrayList<Pelanggan>();;
    private static ArrayList<Pelanggan> arrayPelangganHarian = new ArrayList<Pelanggan>();;
    private static Queue<Integer> arrayLayanan = new LinkedList<Integer>();;
    private static Queue<Integer> arrayMakanan = new LinkedList<Integer>();;

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
            if (spesialis == 'S') {
                arrayKokiS.add(objKoki);
            } else if (spesialis == 'G') {
                arrayKokiG.add(objKoki);
            } else if (spesialis == 'A') {
                arrayKokiA.add(objKoki);
            }
        }

        int jumlahPelanggan = in.nextInt();
        int jumlahKursi = in.nextInt();
        int jumlahHari = in.nextInt();

        for (int i = 0; i < jumlahHari; i++) {
            int jumlahPelangganHariIni = in.nextInt();
            int jumlahPelangganDuduk = 0;
            int jumlahPelangganMasuk = 0;
            for (int j = 0; j < jumlahPelangganHariIni; j++) {
                int idPelanggan = in.nextInt();
                char statusKesehatan = in.next().charAt(0);
                int uangPelanggan = in.nextInt();
                if (statusKesehatan == '?') {
                    int jumlahScanning = in.nextInt();
                    statusKesehatan = advanceScanning(jumlahPelangganMasuk, jumlahScanning);
                }
                Pelanggan objPelanggan = new Pelanggan(idPelanggan, statusKesehatan, uangPelanggan);
                arrayPelangganHarian.add(objPelanggan);
                arrayPelanggan.add(objPelanggan);
                jumlahPelangganMasuk++;

                if (arrayPelanggan.get(j).getBlacklist()) {
                    out.print("3 ");
                } else if (arrayPelanggan.get(j).getKesehatan() == '+') {
                    out.print("0 ");
                } else if (jumlahKursi - 1 < jumlahPelangganDuduk) {
                    out.print("2 ");
                } else {
                    jumlahPelangganDuduk++;
                    out.print("1 ");
                }
            }
            out.println();
            int jumlahLayanan = in.nextInt();
            for (int k = 0; k < jumlahLayanan; k++) {
                char jenisLayanan = in.next().charAt(0);
                if (jenisLayanan == 'P') {
                    int idPelanggan = in.nextInt();
                    int indexMakanan = in.nextInt();
                    arrayLayanan.add(idPelanggan);
                    arrayMakanan.add(indexMakanan);
                    int indexKoki = layananP(idPelanggan - 1, indexMakanan - 1);
                    out.println(indexKoki);
                } else if (jenisLayanan == 'L') {
                    layananL(arrayMakanan.poll() - 1);
                    // out.println(arrayKokiS.first().getPelayanan() + "s");
                    out.println(arrayLayanan.poll());

                } else if (jenisLayanan == 'B') {
                    int idPelanggan = in.nextInt();
                    int statusBayar = layananB(idPelanggan - 1);
                    out.println(statusBayar);
                } else if (jenisLayanan == 'C') {
                    int jumlahLihatKoki = in.nextInt();
                    for (int l = 0; l < jumlahLihatKoki; l++) {
                        int statusKoki = layananC();
                        out.print(statusKoki + " ");
                    }
                } else if (jenisLayanan == 'D') {
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
        char jenisMakanan = arrayMenu.get(indexMakanan).getJenis();
        int hargaMakanan = arrayMenu.get(indexMakanan).getHarga();
        arrayPelangganHarian.get(idPelanggan).setJumlahBayar(hargaMakanan);
        if (jenisMakanan == 'A') {
            arrayKokiLayanan.add(arrayKokiA.first());
            return arrayKokiA.first().getId();
        } else if (jenisMakanan == 'G') {
            arrayKokiLayanan.add(arrayKokiG.first());
            return arrayKokiG.first().getId();
        } else if (jenisMakanan == 'S') {
            arrayKokiLayanan.add(arrayKokiS.first());
            return arrayKokiS.first().getId();
        }
        return -1;
    }

    public static void layananL(int indexMakanan) {
        arrayKokiLayanan.get(0).setPelayanan(arrayKokiLayanan.get(0).getPelayanan() + 1);
        Koki temp_koki = arrayKokiLayanan.remove(0);
        char jenisMakanan = arrayMenu.get(indexMakanan).getJenis();
        if (jenisMakanan == 'A') {
            arrayKokiA.remove(arrayKokiA.first());
            arrayKokiA.add(temp_koki);
        } else if (jenisMakanan == 'G') {
            arrayKokiA.remove(arrayKokiG.first());
            arrayKokiG.add(temp_koki);
        } else if (jenisMakanan == 'S') {
            arrayKokiS.add(temp_koki);
            arrayKokiA.remove(arrayKokiS.first());
        }
        arrayKoki.add(temp_koki);
        arrayKoki.remove(arrayKoki.first());
    }

    public static int layananB(int idPelanggan) {
        int uangPelanggan = arrayPelangganHarian.get(idPelanggan).getUang();
        int jumlahBayar = arrayPelangganHarian.get(idPelanggan).getJumlahBayar();
        if (uangPelanggan < jumlahBayar) {
            arrayPelanggan.get(idPelanggan).setBlacklist(true);
            return 0;
        } else {
            return 1;
        }
    }

    public static int layananC() {
        return -1;
    }

    public static int layananD(int costA, int costG, int costS) {
        return -1;
    }

    public static char advanceScanning(int urutanPelanggan, int jumlahScanning) {
        int positif = 0;
        int negatif = 0;
        for (int i = urutanPelanggan - 1; i >= urutanPelanggan - jumlahScanning; i--) {
            if (arrayPelangganHarian.get(i).getKesehatan() == '+') {
                positif++;
            } else {
                negatif++;
            }
        }
        if (negatif >= positif) {
            return '-';
        } else {
            return '+';
        }
    }
    public static TreeSet<Koki> getArrayKokiA() {
        return arrayKokiA;
    }
    public static TreeSet<Koki> getArrayKokiS() {
        return arrayKokiS;
    }
    public static TreeSet<Koki> getArrayKokiG() {
        return arrayKokiG;
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
    private int jumlahBayar;

    public Pelanggan(int id, char kesehatan, int uang) {
        this.id = id;
        this.kesehatan = kesehatan;
        this.uang = uang;
        this.blacklist = false;
        this.jumlahBayar = 0;
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

    public int getJumlahBayar() {
        return jumlahBayar;
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

    public void setJumlahBayar(int jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
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
        if (this.pelayanan < that.pelayanan) {
            return -1;
        } else if (this.pelayanan > that.pelayanan) {
            return 1;
        } else {
            if (this.spesialis > that.spesialis) {
                return -1;
            } else if (this.spesialis < that.spesialis) {
                return 1;
            } else {
                if (this.id < that.id) {
                    return -1;
                } else if (this.id > that.id) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }

}
class pesanan{
    Pelanggan pelanggan;
    Menu menu; 
    Koki koki;

    public pesanan(Pelanggan pelanggan, Menu menu, Koki koki) {
        this.pelanggan = pelanggan;
        this.menu = menu;
        this.koki = kokiTersedia(menu.getJenis());
    }

    public Koki kokiTersedia(char spesialis){
        if(spesialis == 'S'){
            return sofitayang.getArrayKokiS().first();
        }else if(spesialis == 'G'){
            return sofitayang.getArrayKokiG().first();
        }else{
            return sofitayang.getArrayKokiA().first();
        }
    }

}