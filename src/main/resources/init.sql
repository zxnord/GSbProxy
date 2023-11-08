-- init.sql
CREATE ALIAS IF NOT EXISTS GetProductDesc_withparameters AS $$
String getProductDescWithParameters(int pid) throws SQLException {
    try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=MySQL");
         PreparedStatement stmt = conn.prepareStatement("SELECT P.ProductID, P.ProductName, PD.ProductDescription " +
                     "FROM Product P " +
                     "INNER JOIN ProductDescription PD ON P.ProductID = PD.ProductID " +
                     "WHERE P.ProductID = ?")) {
        stmt.setInt(1, pid);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getString("ProductID") + ", " + rs.getString("ProductName") + ", " + rs.getString("ProductDescription");
        }
        return null;
    }
}
$$;
