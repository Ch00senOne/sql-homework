package com.lab;

import com.lab.models.Periodical;
import com.lab.models.Subscriber;
import com.lab.service.PeriodicalService;
import com.lab.service.SubscribersService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class H2Test {

    public static void main(String[] args) {

        String url = "jdbc:h2:mem:";

        try (
                Connection con = DriverManager.getConnection(url);
                Statement stm = con.createStatement();
        ) {
            initDb(stm);
            SubscribersService subscribersService = new SubscribersService(stm);
            Subscriber subscriber = subscribersService.get(4L);
            System.out.println(subscribersService.getSubscriptions(subscriber));

//            PeriodicalService periodicalService = new PeriodicalService(stm);
//            System.out.println(periodicalService.getAll());
//
//            periodicalService.create(new Periodical("example222"));
//
//            periodicalService.delete(2L);
//
//            System.out.println(periodicalService.getAll());
//            System.out.println("_________");
//            Periodical periodical = periodicalService.get(1L);
//            System.out.println(periodicalService.getSubscribers(periodical));


//      try (final ResultSet resultSet = stm.executeQuery("SELECT * FROM PERIODICAL")) {
//        while (resultSet.next()) {
//          System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2));
//        }
//      }
//      try (final ResultSet resultSet = stm.executeQuery("SELECT * FROM SUBSCRIBERS")) {
//        while (resultSet.next()) {
//          System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2));
//        }
//      }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void initDb(java.sql.Statement stm) throws SQLException {
        stm.execute(getSqlsFromResources(
                "create_periodical_table",
                "create_subscribers_table",
                "create_periodical_to_subscriber_table",
                "add_periodicals",
                "add_subscribers",
                "add_links"));
    }

    private static String getSqlsFromResources(String... resourcesNames) {
        return Arrays.stream(resourcesNames)
                     .map(H2Test::getSqlFromResources)
                     .collect(Collectors.joining(";"));
    }

    private static String getSqlFromResources(String resourceName) {
        try {
            return String.join("",
                    Files.readAllLines(Paths.get(H2Test.class.getClassLoader()
                                                             .getResource(
                                                                     String.format("sql/%s.sql", resourceName))
                                                             .toURI())));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
