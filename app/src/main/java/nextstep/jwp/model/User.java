package nextstep.jwp.model;

public class User {

    private static long CURRENT_ID = 0;

    private final long id;
    private final String account;
    private final String password;
    private final String email;

    public User(long id, String account, String password, String email) {
        validateInputs(id, account, password, email);
        this.id = id;
        this.account = account;
        this.password = password;
        this.email = email;
    }

    public User(String account, String password, String email) {
        this(++CURRENT_ID, account, password, email);
    }

    private void validateInputs(long id, String account, String password, String email) {
        if (id < CURRENT_ID || account == null || password == null || email == null ||
            account.isBlank() || password.isBlank() || email.isBlank()) {
            throw new IllegalArgumentException();
        }
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public long getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", account='" + account + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            '}';
    }
}
