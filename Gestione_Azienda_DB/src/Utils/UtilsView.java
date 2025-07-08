package Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import exceptions.EtaNonValidaException;
import exceptions.RuoloNonValidoException;

public class UtilsView {

    public static Ruolo parseRuolo(String input) throws RuoloNonValidoException {
        try {
            return Ruolo.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuoloNonValidoException("Ruolo non valido. Riprova.");
        }
    }

    public static void validaEta(LocalDate birthDate, int etaMinima) throws EtaNonValidaException {
        long eta = ChronoUnit.YEARS.between(birthDate, LocalDate.now());
        if (eta < etaMinima) {
            throw new EtaNonValidaException("Età non valida: deve avere almeno " + etaMinima + " anni (età attuale: " + eta + ").");
        }
    }

    public static void validaDataAssunzione(LocalDate birthDate, LocalDate dataAssunzione, int etaMinima) throws EtaNonValidaException {
        LocalDate oggi = LocalDate.now();
        LocalDate dataMinimaAssunzione = birthDate.plusYears(etaMinima);
        LocalDate dataMassimaAssunzione = oggi.plusYears(1);

        if (dataAssunzione.isBefore(dataMinimaAssunzione)) {
            throw new EtaNonValidaException("Data di assunzione non valida: la persona deve avere almeno "
                    + etaMinima + " anni (data minima: " + dataMinimaAssunzione + ").");
        }

        if (dataAssunzione.isBefore(oggi)) {
            throw new EtaNonValidaException("Data di assunzione non valida: non può essere una data passata (" + dataAssunzione + ").");
        }

        if (dataAssunzione.isAfter(dataMassimaAssunzione)) {
            throw new EtaNonValidaException("Data di assunzione non valida: non può essere troppo nel futuro (oltre il " + dataMassimaAssunzione + ").");
        }
    }

    public static LocalDate parseData(String input) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(input, formatter);
    }

    public static boolean dataNelFuturo(LocalDate data) {
        return data.isAfter(LocalDate.now());
    }

    public static boolean dataTroppoVecchia(LocalDate data) {
        return data.isBefore(LocalDate.now().minusYears(140));
    }
}
