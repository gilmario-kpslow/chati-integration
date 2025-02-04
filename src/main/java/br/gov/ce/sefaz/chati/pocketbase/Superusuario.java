package br.gov.ce.sefaz.chati.pocketbase;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author gilmario
 */
@Getter
@Setter
@ToString
public class Superusuario {

    private String collectionId;
    private String collectionName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS'Z'", shape = JsonFormat.Shape.STRING)
    private LocalDateTime created;
    private String email;
    private Boolean emailVisibility;
    private String id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS'Z'", shape = JsonFormat.Shape.STRING)
    private LocalDateTime updated;
    private Boolean verified;
}
