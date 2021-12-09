package com.algaworks.algafood.core.rest;

import com.algaworks.algafood.domain.model.Entity;
import lombok.*;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Representation {

    @NonNull
    private Class<? extends Entity> clazz;
    private HashSet<String> fields = new LinkedHashSet<>();
    private LinkedHashMap<String, Class<? extends RepresentationProvider>> associations = new LinkedHashMap<>();

    public void setField(String fieldName) {
        this.fields.add(fieldName);
    }

    public void setAssociation(String fieldName, Class<? extends RepresentationProvider> association) {
        this.associations.put(fieldName, association);
    }

    @Getter
    @RequiredArgsConstructor
    @Setter
    public static class Builder {

        @NonNull
        private Representation representation;

        public static Builder create(Class<? extends Entity> clazz) {
            return new Builder(new Representation(clazz));
        }

        public Builder field(String fieldName) {
            this.representation.setField(fieldName);
            return this;
        }

        public Builder association(String fieldName, Class<? extends RepresentationProvider> association) {
            this.representation.setAssociation(fieldName,association);
            return this;
        }

        public Representation build() {
            return representation;
        }

    }


}
