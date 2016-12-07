
package edu.calpoly.idulkin.podcrust.rest.Episode;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "entity",
    "category",
    "score",
    "type",
    "is_confirmed",
    "timestamps"
})
public class Entity {

    @JsonProperty("entity")
    private String entity;
    @JsonProperty("category")
    private String category;
    @JsonProperty("score")
    private Double score;
    @JsonProperty("type")
    private Object type;
    @JsonProperty("is_confirmed")
    private Boolean isConfirmed;
    @JsonProperty("timestamps")
    private Object timestamps;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The entity
     */
    @JsonProperty("entity")
    public String getEntity() {
        return entity;
    }

    /**
     * 
     * @param entity
     *     The entity
     */
    @JsonProperty("entity")
    public void setEntity(String entity) {
        this.entity = entity;
    }

    /**
     * 
     * @return
     *     The category
     */
    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    /**
     * 
     * @param category
     *     The category
     */
    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 
     * @return
     *     The score
     */
    @JsonProperty("score")
    public Double getScore() {
        return score;
    }

    /**
     * 
     * @param score
     *     The score
     */
    @JsonProperty("score")
    public void setScore(Double score) {
        this.score = score;
    }

    /**
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public Object getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    @JsonProperty("type")
    public void setType(Object type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The isConfirmed
     */
    @JsonProperty("is_confirmed")
    public Boolean getIsConfirmed() {
        return isConfirmed;
    }

    /**
     * 
     * @param isConfirmed
     *     The is_confirmed
     */
    @JsonProperty("is_confirmed")
    public void setIsConfirmed(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    /**
     * 
     * @return
     *     The timestamps
     */
    @JsonProperty("timestamps")
    public Object getTimestamps() {
        return timestamps;
    }

    /**
     * 
     * @param timestamps
     *     The timestamps
     */
    @JsonProperty("timestamps")
    public void setTimestamps(Object timestamps) {
        this.timestamps = timestamps;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
