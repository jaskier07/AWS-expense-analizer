package pl.kania.expensesCounter.commons.dto.grouping;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import pl.kania.expensesCounter.commons.dto.db.ExpenseMapping;

@Value
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class SingleExpense {
    double amount;
    ExpenseMapping mapping;
}
