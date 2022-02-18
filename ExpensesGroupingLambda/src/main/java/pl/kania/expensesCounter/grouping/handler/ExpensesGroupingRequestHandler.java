package pl.kania.expensesCounter.grouping.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import pl.kania.expensesCounter.commons.dto.extraction.ContentExtractionResult;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;
import pl.kania.expensesCounter.commons.dto.db.ExpenseType;
import pl.kania.expensesCounter.commons.dto.grouping.ExpenseGroupingResult;
import pl.kania.expensesCounter.commons.dto.grouping.GroupingResultPerExpenseType;
import pl.kania.expensesCounter.commons.util.Base64RequestReader;
import pl.kania.expensesCounter.commons.util.ObjectMapperProvider;
import pl.kania.expensesCounter.commons.util.RequestHelper;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
public class ExpensesGroupingRequestHandler implements RequestHandler<String, String> {

    private final ObjectMapper objectMapper = new ObjectMapperProvider().get();
    private final Base64RequestReader requestReader = new Base64RequestReader();
    private final RequestHelper requestHelper = new RequestHelper();

    @Override
    public String handleRequest(String encodeInput, Context context) {
        log.info(encodeInput);

        String input = requestReader.readStringBase64Encoded(encodeInput).orElseThrow();

        ContentExtractionResult parsedExpenses = Try.of(() -> objectMapper.readValue(input, ContentExtractionResult.class)).get();
        log.info(parsedExpenses.getExpenses().toString());


        log.info("Finished processing");
        var stub = Optional.of(new ExpenseGroupingResult(Arrays.asList(
                GroupingResultPerExpenseType.builder()
                        .type(ExpenseType.FOOD)
                        .sum(23.)
                        .build(),
                GroupingResultPerExpenseType.builder()
                        .type(ExpenseType.FOOD)
                        .sum(25.11)
                        .build()
        ))).orElseThrow();

        return Try.of(() -> requestHelper.writeObjectAsBase64(stub))
                .onFailure(e -> log.error("Error writing object as json base64 response", e))
                .getOrNull();
    }
}
