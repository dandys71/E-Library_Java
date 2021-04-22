package cz.uhk.fim.student.schmid.java.model;

public enum Genre {
    NONE,
    KRIMI,
    SCI_FI,
    FANTASY,
    ROMAN,
    POVIDKA,
    POEZIE,
    NAUCNA,
    HISTORICKA,
    CESTOPIS,
    UCEBNICE,
    ODBORNA_PUBLIKACE;

    public String getGenreName(){
        return capitalize(name().toLowerCase());
    }

    private String capitalize(String str){
        return str.substring(0, 1).toUpperCase() + str.substring(1).replace("_", "-");
    }
}
