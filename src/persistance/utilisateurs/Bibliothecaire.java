package persistance.utilisateurs;

import persistance.AbsUtilisateur;

public class Bibliothecaire extends AbsUtilisateur {

    public Bibliothecaire(int id, String name, int age, String login, String password) {
        super(id, name, age, login, password);
    }

    @Override
    public boolean isBibliothecaire() {
        return true;
    }
}
