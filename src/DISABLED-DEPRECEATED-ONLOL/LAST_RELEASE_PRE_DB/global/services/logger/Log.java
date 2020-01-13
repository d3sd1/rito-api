/*
 * Copyright (c) 2020.
 * d3sd1.
 * All right reserved.
 * Do not re-distribute this file nor project without permission.
 */

package global.services.logger;

import javax.persistence.*;


/**
 * Log model. Stored on database.
 *
 * @author d3sd1
 * @version 0.0.9
 */
@Entity
@Table(schema = "logging")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private long id;

    @Column(nullable = false, unique = false, columnDefinition = "text")
    private String text;

    @Column(nullable = false, unique = false)
    private LogLevel level;

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets text.
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public LogLevel getLevel() {
        return level;
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(LogLevel level) {
        this.level = level;
    }
}
