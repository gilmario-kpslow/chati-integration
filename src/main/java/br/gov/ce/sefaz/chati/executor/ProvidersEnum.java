package br.gov.ce.sefaz.chati.executor;

import java.util.Arrays;

/**
 *
 * @author gilmario
 */
public enum ProvidersEnum {

    CHATI, GOOGLE;

    public static ProvidersEnum valueOfName(String nome) {
        return Arrays.asList(ProvidersEnum.values()).stream().filter(a -> a.name().equals(nome)).findFirst().orElse(null);
    }
//
//    private final Class<Executor> executor;
//
//    private <T extends Executor> ProvidersEnum(Class<T> executor) {
//        this.executor = executor;
//    }
//
//    public Class<Executor> getExecutor() {
//        return executor;
//    };
}
