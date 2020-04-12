package user;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()) {
            UserDAO dao = handle.attach(UserDAO.class);
            dao.createTable();

            User user = User.builder()
                    .name("James Bond")
                    .username("007")
                    .password("700")
                    .email("james@bond.com")
                    .dob(LocalDate.parse("1920-11-11"))
                    .gender(User.Gender.MALE)
                    .build();
            dao.insert(user);

            System.out.println(dao.findById(1));
            System.out.println(dao.findByUsername("007"));

            dao.delete(dao.findById(1).get());

            dao.list().stream().forEach(System.out::println);
        }
    }
}