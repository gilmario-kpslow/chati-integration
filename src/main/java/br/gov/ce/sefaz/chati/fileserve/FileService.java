package br.gov.ce.sefaz.chati.fileserve;

import br.gov.ce.sefaz.chati.ChatRegistro;
import br.gov.ce.sefaz.chati.utils.JsonConverter;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author gilmario
 */
@Singleton
public class FileService {

    @ConfigProperty(name = "file-store-path")
    Optional<String> fileStorePath;

    private final DateTimeFormatter DEFAULT_FORMATER = DateTimeFormatter.ofPattern("DD-MM-YYYY-HH:mm:ss");

    void save(List<ChatRegistro> lista) {
        // Converter para json e salvar o arquivo com o nome chati-data(yy-mm-dd) e hora(hh-mm-ss);
        fileStorePath.ifPresent(value -> {
            try {
                String json = JsonConverter.toJson(lista);
                String fileName = new StringBuilder("chati").append(LocalDateTime.now().format(DEFAULT_FORMATER)).toString();
                Files.write(Paths.get(value, fileName), json.getBytes());
                Files.write(Paths.get(value, "last-backup.json"), json.getBytes());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    List<String> list() throws IOException {
        if (fileStorePath.isEmpty()) {
            return Collections.emptyList();
        }
        return Files.list(Paths.get(fileStorePath.get())).map(p -> p.toString()).toList();
    }

    List<String> ultimoBackup() throws IOException {
        if (fileStorePath.isEmpty()) {
            return Collections.emptyList();
        }
        return Files.list(Paths.get(fileStorePath.get())).map(p -> p.toString()).toList();
    }

}
