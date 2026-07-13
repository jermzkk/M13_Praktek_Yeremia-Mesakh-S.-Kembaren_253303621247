import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HeroDAO {
    private Connection connection;

    public HeroDAO() throws SQLException {
        this.connection = Database.getConnection();
    }

    public void hapusHero(int id) throws SQLException {
        String sql = "DELETE FROM hero WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
    
    // Tambah metode untuk load data ke tabel
    public List<String[]> getAllHero() throws SQLException {
        List<String[]> list = new ArrayList<>();
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM hero");
        while (rs.next()) {
            list.add(new String[]{String.valueOf(rs.getInt("id")), rs.getString("nama"), rs.getString("tipe")});
        }
        return list;
    }
}