package fr.diginamic.entites;

public class Movie {
    private Integer rank;
    private String nameMovie;
    private Integer yearOfRelease;
    private Float rating;

    /**
     * <p>Constructeur qui crée une instance de film à partir du
     * rang, le titre, la date de sortie et la note du film</p>
     *
     * @param rank              Le rang du film
     * @param nameMovie         Le titre du film
     * @param yearOfRelease     L'année de sortie
     * @param rating            La note
     */
    public Movie(int rank, String nameMovie, int yearOfRelease, float rating) {
        this.rank = rank;
        this.nameMovie = nameMovie;
        this.yearOfRelease = yearOfRelease;
        this.rating = rating;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getNameMovie() {
        return nameMovie;
    }

    public void setNameMovie(String nameMovie) {
        this.nameMovie = nameMovie;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    /**
     * <p>La méthode a pour but de, similairement à toString(), retourner
     * les valeurs de chacun des attributs séparés par des ';' en tant que ligne String </p>
     *
     * @return Une ligne au format CSV
     */
    public String formatToCSVLine(){
        StringBuilder stringBuilder = new StringBuilder(rank.toString());
        return String.valueOf(
                stringBuilder
                        .append(";").append(nameMovie)
                        .append(";").append(yearOfRelease.toString())
                        .append(";").append(rating.toString())
        );
    }

    @Override
    public String toString() {
        return "Movie{" +
                "rank=" + rank +
                ", nameMovie='" + nameMovie + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                ", rating=" + rating +
                '}';
    }
}
