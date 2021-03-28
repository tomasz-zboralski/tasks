package com.crud.tasks.service;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloBoardDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class TrelloServiceTest {

    @Mock
    TrelloService trelloService;

    @Test
    void testFetchTrelloBoards() {

        TrelloBoardDto trelloBoard = new TrelloBoardDto("1","Test", new ArrayList<>());
        List<TrelloBoardDto> trelloBoardList = List.of(trelloBoard);

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoardList);

        int fetchedBoards = trelloService.fetchTrelloBoards().size();

        assertEquals(1, fetchedBoards);


    }
}