package com.cebem.medidor.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Superhero {

    private String id;
    private String name;
    private Powerstats powerstats;
    private Biography biography;
    private Appearance appearance;
    private Work work;
    private Connections connections;
    private Image image;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Powerstats {
        private String intelligence;
        private String strength;
        private String speed;
        private String durability;
        private String power;
        private String combat; 
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Biography {
        @JsonProperty("full-name")
        private String fullName;

        @JsonProperty("place-of-birth")
        private String placeOfBirth;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Appearance {
        private String gender;
        private String race;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Work {
        private String occupation;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Connections {
        @JsonProperty("group-affiliation")
        private String groupAffiliation;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Image {
        private String url;
    }
}
