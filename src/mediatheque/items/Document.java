package mediatheque.items;

public interface Document {
    void reserver(Utilisateur var1) throws ReservationException;

    void emprunter(Utilisateur var1) throws EmpruntException;

    void rendre(Utilisateur var1) throws RetourException;

    Object[] data();
}