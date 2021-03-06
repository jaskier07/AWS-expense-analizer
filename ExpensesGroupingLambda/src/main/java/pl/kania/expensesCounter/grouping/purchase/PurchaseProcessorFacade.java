package pl.kania.expensesCounter.grouping.purchase;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.kania.expensesCounter.commons.dto.TransactionType;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;
import pl.kania.expensesCounter.commons.dto.db.ExpenseMapping;
import pl.kania.expensesCounter.commons.dto.grouping.GroupingResultPerExpenseType;
import pl.kania.expensesCounter.grouping.purchase.card.CardPurchaseProcessor;
import pl.kania.expensesCounter.grouping.search.ExpenseMappingsCardSearch;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class PurchaseProcessorFacade {

    private final ExpenseMappingsCardSearch cardSearch = new ExpenseMappingsCardSearch();
    private final CardPurchaseProcessor cardPurchaseProcessor = new CardPurchaseProcessor(cardSearch);

    public List<GroupingResultPerExpenseType> process(Map<TransactionType, List<ParsedExpense>> expensesPerTransactionType) {
        expensesPerTransactionType.entrySet().stream()
                .map(entry -> {
                    TransactionType transactionType = entry.getKey();
                    List<ParsedExpense> expenses = entry.getValue();

                    switch (transactionType) {
                        case PURCHASE_CARD:
                            return cardPurchaseProcessor.process(expenses);
                        default:
                            // TODO
                            log.warn("Unhandled transaction type: " + transactionType);
                            return new ArrayList<GroupingResultPerExpenseType>();
                    }
                })
                .filter(Objects::nonNull);
        return null;// TODO
//                .flatMap(Collection::stream)
//                .collect(Collectors.toList());
    }
}
