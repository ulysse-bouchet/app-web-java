package persistance;

import mediatheque.items.Utilisateur;

public abstract class AbsUtilisateur implements Utilisateur {
    private final int id;
    private final String name;
    private final int age;

    private final String login;
    private final String password;

    public AbsUtilisateur (int id, String name, int age, String login, String password) {
        this.id = id;
        this.name = name;
        this.age = age;

        this.login = login;
        this.password = password;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Object[] data() {
        return new Object[]{id, login, password, age};
    }
}
