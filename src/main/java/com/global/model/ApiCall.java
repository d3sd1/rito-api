package com.global.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.global.configuration.Constants;

import javax.persistence.*;

@Entity
@Table(schema = "logging")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiCall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = false, columnDefinition = "text")
    private String json;

    @OneToOne
    private ApiKey apiKey;

    @Column(nullable = true, unique = false)
    private Constants.CALL_TYPE callType = Constants.CALL_TYPE.GET;

    @Column(nullable = false, unique = false, columnDefinition = "text")
    private Integer elapsedMilliseconds;

    @Column(nullable = false, unique = false)
    private Integer responseCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public ApiKey getApiKey() {
        return apiKey;
    }

    public void setApiKey(ApiKey apiKey) {
        this.apiKey = apiKey;
    }

    public Integer getElapsedMilliseconds() {
        return elapsedMilliseconds;
    }

    public void setElapsedMilliseconds(Integer elapsedMilliseconds) {
        this.elapsedMilliseconds = elapsedMilliseconds;
    }

    public Constants.CALL_TYPE getCallType() {
        return callType;
    }

    public void setCallType(Constants.CALL_TYPE callType) {
        this.callType = callType;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public String toString() {
        return "ApiCall{" +
                "id=" + id +
                ", json='" + json + '\'' +
                ", apiKey=" + apiKey +
                ", callType=" + callType +
                ", elapsedMilliseconds=" + elapsedMilliseconds +
                ", responseCode=" + responseCode +
                '}';
    }
}
