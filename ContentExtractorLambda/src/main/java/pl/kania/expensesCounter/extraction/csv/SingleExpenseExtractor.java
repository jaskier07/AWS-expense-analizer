package pl.kania.expensesCounter.extraction.csv;

import org.apache.commons.csv.CSVRecord;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;

public interface SingleExpenseExtractor {

    ParsedExpense extract(CSVRecord record);

}
