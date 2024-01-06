package poiAPI.office.samples.excel;

import jakarta.persistence.EntityManager;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;

import static com.sun.xml.internal.ws.encoding.SOAPBindingCodec.UTF8_ENCODING;

@RequiredArgsConstructor
public class PostgreCursorXlsxSx {

    private final EntityManager entityManager;
    private final Session session;

    public void createFile(String fileLocation) throws IOException {
        //https://jdbc.postgresql.org/documentation/query/
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/poi_upload", "root", "123")) {
            createTestData(connection, 25);
            // make sure autocommit is off
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            // Turn use of the cursor on.
            statement.setFetchSize(2);
            System.out.println("statement.getQueryTimeout():" + statement.getQueryTimeout());
            System.out.println("statement.getFetchSize():" + statement.getFetchSize());
            System.out.println("statement.getFetchDirection():" + statement.getFetchDirection());
            System.out.println("statement.getMaxFieldSize():" + statement.getMaxFieldSize());
            ResultSet resultSet = statement.executeQuery("SELECT * FROM poi_person");

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            Class resultSetClass = resultSet.getClass();
            Field currentRowField = resultSetClass.getDeclaredField("currentRow");
            currentRowField.setAccessible(true);
            Object currentRowValur = currentRowField.get(resultSet);
            System.out.println("resultSet.currentRow " + currentRowValur);
            Field rowsField = resultSetClass.getDeclaredField("rows");
            rowsField.setAccessible(true);
            Object rowsSize = ((List) rowsField.get(resultSet)).size();
            System.out.println("resultSet.rowsSize " + rowsSize);
            List<Object> bytesList = (List<Object>) rowsField.get(resultSet);
            System.out.println("bytesList " + bytesList);
            for (int i = 0; i < (int) rowsSize; ++i) {
                Class bytesListClass = (bytesList.get(i)).getClass();
                System.out.println("bytesListClass " + bytesListClass);
                Field dataField = bytesListClass.getDeclaredField("data");
                System.out.println("dataField " + dataField);
                dataField.setAccessible(true);
                byte[][] bytes = (byte[][]) dataField.get(bytesList.get(i));
                for (int j = 0; j < resultSetMetaData.getColumnCount(); j++) {
                    System.out.println("bytes[" + j + "] "  + bytes[j]);
                    String encodedString = new String(bytes[j], 0, bytes[j].length, UTF8_ENCODING);
                    System.out.println("encodedString "  + encodedString);
                }
                System.out.println("bytes " + bytes);
            }
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                System.out.println(String.format("-- Column %d --", i));
                System.out.println(String.format("Column name: %s", resultSetMetaData.getColumnName(i)));
                System.out.println(String.format("Database-specific type name: %s", resultSetMetaData.getColumnTypeName(i)));
                System.out.println(String.format("Column size (DisplaySize): %d", resultSetMetaData.getColumnDisplaySize(i)));
                System.out.println(String.format("java.sql.Type of column: %d", resultSetMetaData.getColumnType(i)));
                System.out.println();
            }
            while (resultSet.next()) {
                System.out.print(resultSet.getRow());
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    System.out.print(" " + resultSet.getObject(i));
                }
                System.out.println("");
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void createTestData(Connection connection, int dataCount) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO poi_person(id, age, first_name, last_name) " +
                            "VALUES(?, ?, ?, ?)"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (int i = 1; i <= dataCount; ++i) {
            try {
                preparedStatement.setInt(1, i + 14);
                preparedStatement.setInt(2, i);
                preparedStatement.setString(3, "first_name_" + i);
                preparedStatement.setString(4, "last_name_" + i);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

