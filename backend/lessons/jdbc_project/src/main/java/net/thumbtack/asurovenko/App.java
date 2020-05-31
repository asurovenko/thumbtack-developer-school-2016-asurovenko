package net.thumbtack.asurovenko;


import java.sql.*;

public class App {
    private static final String URL = "jdbc:mysql://172.16.9.5:3306/netexam3";
    private static final String USER = "user3";
    private static final String PASSWORD = "user3";

    private static boolean loadDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return true;
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading JDBC Driver");
            e.printStackTrace();
            return false;
        }
    }

    private static void getAllBooks(Connection con, String query) {
        System.out.println("All books : ");
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Book book = new Book(rs.getInt("id"), rs.getString("title"), rs.getInt("year"),
                        rs.getInt("pages"), rs.getString("publishing_house"), rs.getInt("binding"));
                System.out.println(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("End all books");
    }

    private static void getFirstBooks(Connection con, String query, int n) {
        query += String.valueOf(n);
        System.out.println("First " + n + " books : ");
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Book book = new Book(rs.getInt("id"), rs.getString("title"), rs.getInt("year"),
                        rs.getInt("pages"), rs.getString("publishing_house"), rs.getInt("binding"));
                System.out.println(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("End first " + n + " books");
    }

    private static void getBooksById(Connection con, String query, int id) {
        query += String.valueOf(id);
        System.out.println(id + " book : ");
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                Book book = new Book(rs.getInt("id"), rs.getString("title"), rs.getInt("year"),
                        rs.getInt("pages"), rs.getString("publishing_house"), rs.getInt("binding"));
                System.out.println(book);
            } else {
                System.out.print(" Book not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getBooksByTitle(Connection con, String query, String title) {
        query += title + "\'";
        System.out.println(title + "books : ");
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Book book = new Book(rs.getInt("id"), rs.getString("title"), rs.getInt("year"),
                        rs.getInt("pages"), rs.getString("publishing_house"), rs.getInt("binding"));
                System.out.println(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("End " + title + "books");
    }

    private static void getBooksW(Connection con, String query) {
        System.out.println("Books : ");
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Book book = new Book(rs.getInt("id"), rs.getString("title"), rs.getInt("year"),
                        rs.getInt("pages"), rs.getString("publishing_house"), rs.getInt("binding"));
                System.out.println(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("End books");
    }

    private static void getBooksBy(Connection con, String query, int start, int end) {
        query = String.format(query, start, end);
        System.out.println("Books : ");
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Book book = new Book(rs.getInt("id"), rs.getString("title"), rs.getInt("year"),
                        rs.getInt("pages"), rs.getString("publishing_house"), rs.getInt("binding"));
                System.out.println(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("End books");
    }

    private static void getBooksByYearAndPages(Connection con, String query,
                                               int startYear, int endYear, int startPages, int endPages) {
        query = String.format(query, startYear, endYear, startPages, endPages);
        System.out.println("Books : ");
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Book book = new Book(rs.getInt("id"), rs.getString("title"), rs.getInt("year"),
                        rs.getInt("pages"), rs.getString("publishing_house"), rs.getInt("binding"));
                System.out.println(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("End books");
    }

    private static void updatePages(Connection con, String query, int pages) {
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, pages);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updatePagesWhereMorePages(Connection con, String query, int pages, int minPages) {
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, pages);
            stmt.setInt(2, minPages);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteWherePagesMore(Connection con, String query, int pages) {
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, pages);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteBooks(Connection con, String query) {
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteBetweenId(Connection con, String query, int id1, int id2) {
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, id1);
            stmt.setInt(2, id2);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteByTitle(Connection con, String query, String title) {
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteTitleOf(Connection con, String query, String title1, String title2, String title3) {
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, title1);
            stmt.setString(2, title2);
            stmt.setString(3, title3);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void updateBinding(Connection con, String query) {
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updatePublishingHouseById(Connection con, String query, int id1, int id2, String publishingHouse) {
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, publishingHouse);
            stmt.setInt(2, id1);
            stmt.setInt(3, id2);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void execute(Connection con, String query) {
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String args[]) {
        if (!loadDriver()) return;

        String selectAll = "select * from books;";
        String selectFirstN = "select * from books limit ";
        String selectById = "SELECT * from books WHERE id=";
        String selectByTitle = "SELECT * from books WHERE title=\'";
        String selectBooksStartW = "SELECT * from books WHERE title like 'w%';";
        String selectBooksEndW = "SELECT * from books WHERE title like '%w';";
        String selectBooksByYear = "SELECT * from books where year>%d and year<%d;";
        String selectBooksByPages = "SELECT * from books where pages>%d and pages<%d;";
        String selectBooksByYearAndPages = "SELECT * from books where year>%d and year<%d and pages>%d and pages<%d;";

        String updatePages = "UPDATE books set pages = ?;";
        String updatePagesWhereMorePages = "UPDATE books set pages = ? where pages>?;";
        String updatePagesWhereMorePagesAndStartWithW = "UPDATE books set pages = ? where pages>? and title like 'w%';";

        String deleteWherePagesMore = "DELETE from books where pages>?;";
        String deleteStartWith = "DELETE from books where title like 'w%';";
        String deleteBetweenId = "DELETE from books where id>? and id<?;";
        String deleteByTitle = "DELETE from books where title =?;";
        String deleteTitleOf = "DELETE from books where title IN (?, ?, ?);";
        String deleteAll = "DELETE from books;";

        String updateBinding = "UPDATE books set binding=3;";
        String updatePublishingHouseById = "UPDATE books set publishing_house=? WHERE  id>? and id<?;";

        String renameQuery = "ALTER TABLE books CHANGE title bookname varchar(50) NOT NULL;";
        String renameTableQuery = "ALTER TABLE books rename note;";
        String deleteTable = "DELETE from note;";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            /*getAllBooks(con, selectAll);
            getFirstBooks(con, selectFirstN, 3);
            getBooksById(con, selectById, 2);
            getBooksByTitle(con, selectByTitle, "SKAZKI");
            getBooksW(con, selectBooksStartW);
            getBooksW(con, selectBooksEndW);
            getBooksBy(con, selectBooksByYear, 2000,2010);
            getBooksBy(con, selectBooksByPages, 200,800);
            getBooksByYearAndPages(con, selectBooksByYearAndPages, 1000,2000,0,500);

            updatePages(con, updatePages, 7);
            updatePagesWhereMorePages(con, updatePagesWhereMorePages, 18, 3);
            updatePagesWhereMorePages(con, updatePagesWhereMorePagesAndStartWithW, 21, 3);

            deleteWherePagesMore(con, deleteWherePagesMore, 20);
            deleteBooks(con, deleteStartWith);
            deleteBetweenId(con, deleteBetweenId, 1,3);
            deleteByTitle(con, deleteByTitle, "SKAZKI");
            deleteTitleOf(con, deleteTitleOf, "SKAZKI","wabsa","Sobache Serdce");
            deleteBooks(con, deleteAll);

            updateBinding(con, updateBinding);
            updatePublishingHouseById(con, updatePublishingHouseById, 1, 4, "Omsk");

            execute(con, renameQuery);
            execute(con, renameTableQuery);
            execute(con, deleteTable);*/

            

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }
}
