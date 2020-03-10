package persistance.documents;

import mediatheque.items.EmpruntException;
import mediatheque.items.ReservationException;
import mediatheque.items.Utilisateur;
import persistance.AbsDocument;

import java.sql.Connection;
import java.sql.SQLException;

public class DVD extends AbsDocument {
    private final int ageMin;

    public DVD (int id, String title, String author, Utilisateur currentUser, int ageMin, Connection conn) throws SQLException {
        super(id, title, author, currentUser, conn);
        this.ageMin = ageMin;
    }

    @Override
    public void emprunter(Utilisateur user) throws EmpruntException {
        if ((int) user.data()[3] < ageMin)
            throw new EmpruntException();
        super.emprunter(user);
    }

    @Override
    public void reserver(Utilisateur user) throws ReservationException {
        if ((int) user.data()[3] < ageMin)
            throw new ReservationException();
        super.reserver(user);
    }

    @Override
    protected String toDisplay() {
        return "[DVD] " + super.toDisplay() + (ageMin != 0 ? " | interdit aux moins de " + ageMin + " ans" : "");
    }
}
