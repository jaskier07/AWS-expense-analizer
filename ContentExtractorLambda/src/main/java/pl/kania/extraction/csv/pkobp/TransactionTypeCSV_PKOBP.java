package pl.kania.extraction.csv.pkobp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.kania.extraction.model.TransactionType;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Getter
public enum TransactionTypeCSV_PKOBP {
    INCOMING_TRANSFER(
            new String[]{"Przelew na rachunek", "Przelew na telefon przychodz. zew.", "Zwrot w terminalu", "Zwrot płatności kartą"},
            TransactionType.INCOMING_TRANSFER
    ),
    OUTGOING_TRANSFER(
            new String[]{"Przelew z rachunku", "Przelew na telefon wychodzący zew.", "Przelew na telefon wychodzący wew.", "Zlecenie stałe"},
            TransactionType.OUTGOING_TRANSFER
    ),
    PURCHASE_CARD(
            new String[]{"Płatność kartą"},
            TransactionType.PURCHASE_CARD
    ),
    PURCHASE_WEB_MOBILE_CODE(
            new String[]{"Płatność web - kod mobilny", "Zakup w terminalu - kod mobilny"},
            TransactionType.PURCHASE_WEB_MOBILE_CODE
    ),
    WITHDRAWAL(
            new String[]{"Wypłata z bankomatu"},
            TransactionType.WITHDRAWAL
    ),
    OTHER(
            new String[]{"Przelew Paybynet"},
            TransactionType.OTHER
    );

    private final String[] names;
    private final TransactionType transactionType;

    TransactionTypeCSV_PKOBP(String[] names, TransactionType transactionType) {
        this.names = mapToWindows1250(names);
        this.transactionType = transactionType;
    }

    public static TransactionTypeCSV_PKOBP from(String name) {
        return Arrays.stream(values())
                .filter(type -> Arrays.asList(type.names).contains(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown transaction type: " + name));
    }

    private String[] mapToWindows1250(String[] names) {
        return Arrays.stream(names)
                .map(name -> {
                    byte[] bytes = name.getBytes(Charset.forName("Windows-1250"));
                    return new String(bytes, StandardCharsets.UTF_8);
                })
                .toArray(String[]::new);
    }
}