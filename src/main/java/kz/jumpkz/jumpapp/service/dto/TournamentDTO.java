package kz.jumpkz.jumpapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link kz.jumpkz.jumpapp.domain.Tournament} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TournamentDTO implements Serializable {

    private Long id;

    @NotNull
    private String tournamentName;

    private LocationDTO location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TournamentDTO)) {
            return false;
        }

        TournamentDTO tournamentDTO = (TournamentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tournamentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TournamentDTO{" +
            "id=" + getId() +
            ", tournamentName='" + getTournamentName() + "'" +
            ", location=" + getLocation() +
            "}";
    }
}
