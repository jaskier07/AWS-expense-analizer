package pl.kania.extraction.pdf.parsing;

import pl.kania.extraction.model.ParsedExpense;

import java.util.Optional;

public class CardPurchaseExpenseParser implements ExpenseParserBasedOnOperationType {
    @Override
    public Optional<ParsedExpense> parse(SeparatedExpense expense) {
        return Optional.empty();
    }
}
