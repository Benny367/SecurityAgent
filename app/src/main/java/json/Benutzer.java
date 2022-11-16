package json;

public class Benutzer {
    private int anzVersuche;
    private String email;
    private boolean lokalSpeichern;
    private boolean aktiv;
    private String passwort;

    public int getAnzVersuche() {
        return anzVersuche;
    }

    public void setAnzVersuche(int anzVersuche) {
        this.anzVersuche = anzVersuche;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLokalSpeichern() {
        return lokalSpeichern;
    }

    public void setLokalSpeichern(boolean lokalSpeichern) {
        this.lokalSpeichern = lokalSpeichern;
    }

    public boolean isAktiv() {
        return aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    @Override
    public String toString() {
        return "Benutzer{" +
                "anzVersuche=" + anzVersuche +
                ", email='" + email + '\'' +
                ", lokalSpeichern=" + lokalSpeichern +
                ", aktiv=" + aktiv +
                ", passwort='" + passwort + '\'' +
                '}';
    }
}
