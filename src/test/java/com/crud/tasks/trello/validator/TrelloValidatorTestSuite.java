package com.crud.tasks.trello.validator;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrelloValidatorTestSuite {

    @Test
    void validateCard() {
        //Given
        Logger validateCardLogger = (Logger) LoggerFactory.getLogger(TrelloValidator.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        validateCardLogger.addAppender(listAppender);

        //When
        TrelloCard trelloCard = new TrelloCard("test", "test", "test", "test");
        TrelloValidator trelloValidator = new TrelloValidator();

        trelloValidator.validateCard(trelloCard);

        List<ILoggingEvent> logsList = listAppender.list;
        String loggerInfo = String.valueOf(logsList.get(0));

        //Then
        assertEquals("[INFO] Someone is testing my application!", loggerInfo);

    }

    @Test
    void validateTrelloBoards() {
        //Given
        Logger validateCardLogger = (Logger) LoggerFactory.getLogger(TrelloValidator.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        validateCardLogger.addAppender(listAppender);

        List<TrelloList> lists = new ArrayList<>();
        TrelloBoard trelloBoard1 = new TrelloBoard("1", "test", lists);
        TrelloBoard trelloBoard2 = new TrelloBoard("1", "legit board", lists);

        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        trelloBoardList.add(trelloBoard1);
        trelloBoardList.add(trelloBoard2);

        //When
        TrelloValidator trelloValidator = new TrelloValidator();
        List<TrelloBoard> validatedList = trelloValidator.validateTrelloBoards(trelloBoardList);

        List<ILoggingEvent> logsList = listAppender.list;
        String loggerInfo = String.valueOf(logsList.get(0));

        //Then
        assertEquals(1, validatedList.size());
    }
}