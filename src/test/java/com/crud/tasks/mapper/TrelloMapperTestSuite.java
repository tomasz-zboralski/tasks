package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@SpringBootTest
public class TrelloMapperTestSuite {

    @Autowired
    TrelloMapper trelloMapper;

    @Test
    void testMapToBoards() {
        //Given
        TrelloList trelloList = new TrelloList("1", "test list", false);
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(trelloList);
        TrelloBoard trelloBoard = new TrelloBoard("1","test", trelloLists);
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard);
        List<TrelloBoardDto> trelloBoardDtoList= trelloMapper.mapToBoardsDto(trelloBoards);

        //When
        String testedId = trelloBoardDtoList.get(0).getId();
        String testedName = trelloBoardDtoList.get(0).getName();
        List<TrelloListDto> testedTrelloList = trelloBoardDtoList.get(0).getLists();

        //Then
        Assertions.assertEquals("1", testedId);
        Assertions.assertEquals("test", testedName);
        Assertions.assertEquals(1, testedTrelloList.size());

    }

    @Test
    void testMapToBoardsDto() {
        //Given
        TrelloListDto trelloList = new TrelloListDto("1", "test list", false);
        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        trelloListDtoList.add(trelloList);
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("1","test", trelloListDtoList);
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(trelloBoardDto);
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtoList);

        //When
        String testedId = trelloBoards.get(0).getId();
        String testedName = trelloBoards.get(0).getName();
        List<TrelloList> testedTrelloList = trelloBoards.get(0).getLists();

        //Then
        Assertions.assertEquals("1", testedId);
        Assertions.assertEquals("test", testedName);
        Assertions.assertEquals(1, testedTrelloList.size());

    }

    @Test
    void testMapToList() {

        //Given
        TrelloListDto trelloList = new TrelloListDto("1", "test list", false);
        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        trelloListDtoList.add(trelloList);

        List<TrelloList> testedTrelloList = trelloMapper.mapToList(trelloListDtoList);

        //When
        String testedId = testedTrelloList.get(0).getId();
        String testedName = testedTrelloList.get(0).getName();
        boolean testedIsClosed = testedTrelloList.get(0).isClosed();

        //Then
        Assertions.assertEquals("1", testedId);
        Assertions.assertEquals("test list", testedName);
        Assertions.assertEquals(false, testedIsClosed);
    }

    @Test
    void testMapToListDto() {

        //Given
        TrelloList trelloList = new TrelloList("1", "test list", false);
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(trelloList);

        List<TrelloListDto> testedTrelloListDto = trelloMapper.mapToListDto(trelloLists);

        //When
        String testedId = testedTrelloListDto.get(0).getId();
        String testedName = testedTrelloListDto.get(0).getName();
        boolean testedIsClosed = testedTrelloListDto.get(0).isClosed();

        //Then
        Assertions.assertEquals("1", testedId);
        Assertions.assertEquals("test list", testedName);
        Assertions.assertEquals(false, testedIsClosed);
    }

    @Test
    void testMapToCardDto() {

        //Given
        TrelloCard trelloCard = new TrelloCard(
                "TestName", "TestDesc", "TestPos", "TestListId");

        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //When
        String name = trelloCardDto.getName();
        String description = trelloCardDto.getDescription();
        String pos = trelloCardDto.getPos();
        String listId = trelloCardDto.getListId();

        //Then
        Assertions.assertEquals("TestName", name);
        Assertions.assertEquals("TestDesc", description);
        Assertions.assertEquals("TestPos", pos);
        Assertions.assertEquals("TestListId", listId);

    }

    void testMapToCard() {

        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "TestName", "TestDesc", "TestPos", "TestListId");

        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //When
        String name = trelloCard.getName();
        String description = trelloCard.getDescription();
        String pos = trelloCard.getPos();
        String listId = trelloCard.getListId();

        //Then
        Assertions.assertEquals("TestName", name);
        Assertions.assertEquals("TestDesc", description);
        Assertions.assertEquals("TestPos", pos);
        Assertions.assertEquals("TestListId", listId);

    }
}
