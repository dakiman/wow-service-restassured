package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown=true)
public class HeroCharacter {
    public String name, realm;
    public int id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HeroCharacter that = (HeroCharacter) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return realm != null ? realm.equals(that.realm) : that.realm == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (realm != null ? realm.hashCode() : 0);
        return result;
    }

    public HeroCharacter() {}

}
