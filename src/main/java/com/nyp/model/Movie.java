package com.nyp.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.springframework.data.annotation.Id;

/**
 * @projectName: dydatasource
 * @package: com.nyp.model
 * @className: Movie
 * @author: nyp
 * @description: TODO
 * @date: 2023/6/13 9:01
 * @version: 1.0
 */
public class Movie {
    private String title;
    private Integer version;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
