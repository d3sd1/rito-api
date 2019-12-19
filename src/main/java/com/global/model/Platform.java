/*
 * Copyright (c) 2019.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package com.global.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Platform (region).
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Entity
@Table(schema = "common")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Platform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer id;

    @Column(nullable = false, unique = false) // DUE TO NA!!
    private String serviceRegion;

    @Column(nullable = false, unique = true)
    private String servicePlatform;

    @Column(nullable = false, unique = false) // DUE TO NA!!
    private String hostName;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets service region.
     *
     * @return the service region
     */
    public String getServiceRegion() {
        return serviceRegion;
    }

    /**
     * Sets service region.
     *
     * @param serviceRegion the service region
     */
    public void setServiceRegion(String serviceRegion) {
        this.serviceRegion = serviceRegion;
    }

    /**
     * Gets service platform.
     *
     * @return the service platform
     */
    public String getServicePlatform() {
        return servicePlatform;
    }

    /**
     * Sets service platform.
     *
     * @param servicePlatform the service platform
     */
    public void setServicePlatform(String servicePlatform) {
        this.servicePlatform = servicePlatform;
    }

    /**
     * Gets host name.
     *
     * @return the host name
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Sets host name.
     *
     * @param hostName the host name
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    @Override
    public String toString() {
        return "Platform{" +
                "id=" + id +
                ", serviceRegion='" + serviceRegion + '\'' +
                ", servicePlatform='" + servicePlatform + '\'' +
                ", hostName='" + hostName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object platform) {
        boolean retVal = false;

        if (platform instanceof Platform) {
            Platform ptr = (Platform) platform;
            retVal = ptr.getId() == this.id;
        }

        return retVal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

}
