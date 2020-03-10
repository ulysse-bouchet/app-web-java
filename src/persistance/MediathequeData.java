package persistance;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import mediatheque.*;
import mediatheque.items.*;
import persistance.documents.DVD;
import persistance.documents.Livre;
import persistance.utilisateurs.Abonne;
import persistance.utilisateurs.Bibliothecaire;

import javax.annotation.Resource;

// classe mono-instance dont l'unique instance est inject�e dans Mediatheque
// via une auto-d�claration dans son bloc static
@Resource
public class MediathequeData implements PersistentMediatheque {
    public static final int TYPE_LIVRE = 0;
    public static final int TYPE_DVD = 1;

    public static final int ROLE_ABO = 0;
    public static final int ROLE_BIBLI = 1;

    private final List<Document> documentsCache;
    private final List<Utilisateur> usersCache;

    private Connection conn;

    private PreparedStatement getTousLesDocuments;
    private PreparedStatement getUserByLoginAndPassword;
    private PreparedStatement getUserById;
    private PreparedStatement getDocument;

    private PreparedStatement insertLivre;
    private PreparedStatement insertDVD;

    // Jean-Fran�ois Brette 01/01/2018
    static {
        Mediatheque.getInstance().setData(new MediathequeData());
    }

    private MediathequeData() {
        usersCache = new LinkedList<>();
        documentsCache = new LinkedList<>();

        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) { e.printStackTrace(); }

        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                    "ulysse",
                    "password");
            getTousLesDocuments = conn.prepareStatement("SELECT * FROM DOCUMENT ORDER BY ID");
            getUserByLoginAndPassword = conn.prepareStatement("SELECT * FROM UTILISATEUR WHERE LOGIN = ? AND PASSWORD = ?");
            getUserById = conn.prepareStatement("SELECT * FROM UTILISATEUR WHERE ID = ?");
            getDocument = conn.prepareStatement("SELECT * FROM DOCUMENT WHERE ID = ?");
            insertLivre = conn.prepareStatement("INSERT INTO DOCUMENT(ID, TYPE, TITLE, AUTHOR) VALUES (SEQ_DOC.nextval, ?, ?, ?)");
            insertDVD = conn.prepareStatement("INSERT INTO DOCUMENT(ID, TYPE, TITLE, AUTHOR, AGEMIN) VALUES (SEQ_DOC.nextval, ?, ?, ?, ?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // renvoie la liste de tous les documents de la biblioth�que
    @Override
    public List<Document> tousLesDocuments() {
        List<Document> documents = new LinkedList<>();
        try {
            ResultSet results = getTousLesDocuments.executeQuery();
            while (results.next()) {
                int id = results.getInt("id");
                String title = results.getString("title");
                String author = results.getString("author");
                Utilisateur user = getUser(results.getInt("idEmprunteur"));

                switch (results.getInt("type")) {
                    case TYPE_LIVRE:
                        documents.add(new Livre(id, title, author, user, conn));
                        break;
                    case TYPE_DVD:
                        int ageMin = results.getInt("ageMin");
                        documents.add(new DVD(id, title, author, user, ageMin, conn));
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documents;
    }

    // va r�cup�rer le User dans la BD et le renvoie
    // si pas trouv�, renvoie null
    @Override
    public Utilisateur getUser (String login, String password) {
        Utilisateur u = usersCache.stream().filter (
                (user) -> user.data()[1].equals(login) && user.data()[2].equals(password)
        ).findFirst().orElse(null);

        if (u == null) {
            try {
                getUserByLoginAndPassword.setString(1, login);
                getUserByLoginAndPassword.setString(2, password);
                ResultSet result = getUserByLoginAndPassword.executeQuery();
                if (result.next()) {
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    int age = result.getInt("age");

                    switch (result.getInt("role")) {
                        case ROLE_ABO:
                            u = new Abonne(id, name, age, login, password);
                            break;
                        case ROLE_BIBLI:
                            u = new Bibliothecaire(id, name, age, login, password);
                            break;
                    }

                    usersCache.add(u);
                    if (usersCache.size() > 10) usersCache.remove(0);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return u;
    }

    private Utilisateur getUser (int id) {
        Utilisateur u = usersCache.stream().filter (
            (user) -> user.data()[0].equals(id)
        ).findFirst().orElse(null);

        if (u == null) {
            try {
                getUserById.setInt(1, id);
                ResultSet result = getUserById.executeQuery();
                if (result.next()) {
                    String name = result.getString("name");
                    int age = result.getInt("age");
                    String login = result.getString("login");
                    String password = result.getString("password");

                    switch (result.getInt("role")) {
                        case ROLE_ABO:
                            u = new Abonne(id, name, age, login, password);
                            break;
                        case ROLE_BIBLI:
                            u = new Bibliothecaire(id, name, age, login, password);
                            break;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return u;
    }

    // va r�cup�rer le document de num�ro numDocument dans la BD
    // et le renvoie
    // si pas trouv�, renvoie null
    @Override
    public Document getDocument(int numDocument) {
        Document d = documentsCache.stream().filter(
                (document) -> document.data()[1].equals(numDocument)
        ).findFirst().orElse(null);

        if (d == null) {
            try {
                getDocument.setInt(1, numDocument);
                ResultSet result = getDocument.executeQuery();
                if (result.next()) {
                    String title = result.getString("title");
                    String author = result.getString("author");
                    Utilisateur user = getUser(result.getInt("idEmprunteur"));

                    switch (result.getInt("type")) {
                        case TYPE_LIVRE:
                            d = new Livre(numDocument, title, author, user, conn);
                            break;
                        case TYPE_DVD:
                            int ageMin = result.getInt("ageMin");
                            d = new DVD(numDocument, title, author, user, ageMin, conn);
                            break;
                    }

                    List<Document> toUnCache = new LinkedList<>();
                    for (Document doc : documentsCache)
                        if(doc.data()[4] == null)
                            toUnCache.add(doc);
                    documentsCache.removeAll(toUnCache);

                    documentsCache.add(d);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return d;
    }

    // args[0] -> le titre
    // args [1] --> l'auteur
    // etc...
    @Override
    public void nouveauDocument(int type, Object... args) {
        try {
            switch (type) {
                case TYPE_LIVRE:
                    insertLivre.setInt(1, TYPE_LIVRE);
                    insertLivre.setString(2, (String) args[0]);
                    insertLivre.setString(3, (String) args[1]);
                    insertLivre.executeQuery();
                    break;
                case TYPE_DVD:
                    insertDVD.setInt(1, TYPE_DVD);
                    insertDVD.setString(2, (String) args[0]);
                    insertDVD.setString(3, (String) args[1]);
                    insertDVD.setInt(4, Integer.parseInt( (String) args[2] ));
                    insertDVD.executeQuery();
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
