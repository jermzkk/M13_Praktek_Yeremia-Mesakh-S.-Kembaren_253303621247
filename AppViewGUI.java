import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class AppViewGUI extends JFrame {
    private JTable jTable;
    private DefaultTableModel model;
    private JButton btnHapus = new JButton("Hapus Data");
    private JPanel panelInput = new JPanel();
    private HeroDAO dao;

    public AppViewGUI() {
        try { dao = new HeroDAO(); } catch (SQLException e) { e.printStackTrace(); }
        
        setTitle("Aplikasi Hero");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new Object[]{"ID", "Nama", "Tipe"}, 0);
        jTable = new JTable(model);
        add(new JScrollPane(jTable), BorderLayout.CENTER);

        panelInput.add(btnHapus);
        add(panelInput, BorderLayout.SOUTH);

        // --- LOGIKA TOMBOL HAPUS (SESUAI INSTRUKSI) ---
        btnHapus.addActionListener(e -> {
            int row = jTable.getSelectedRow();
            
            // 4. Validasi jika tidak ada baris yang diklik
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Pilih baris yang mau dihapus dulu!");
            } else {
                // 5. Eksekusi hapus
                // SYARAT MUTLAK: Komentar warna kuning/berawalan //
                // // Konversi: mengambil data dari JTable (Object), diubah ke String, lalu ke int
                int idTarget = Integer.parseInt(jTable.getValueAt(row, 0).toString());
                
                try {
                    dao.hapusHero(idTarget);
                    loadDataTable(); // Refresh tabel
                    JOptionPane.showMessageDialog(null, "Data berhasil dihapus dari Database!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        loadDataTable();
        setVisible(true);
    }

    private void loadDataTable() {
        model.setRowCount(0);
        try {
            for (String[] data : dao.getAllHero()) {
                model.addRow(data);
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static void main(String[] args) { new AppViewGUI(); }
}