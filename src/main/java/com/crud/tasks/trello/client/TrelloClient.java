package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.trello.config.TrelloConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.logging.ErrorManager;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TrelloClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);

    private final RestTemplate restTemplate;
    private final TrelloConfig trelloConfig;

//    @Value("${trello.api.endpoint.prod}")
//    private String trelloApiEndpoint;
//    @Value("${trello.app.key}")
//    private String trelloAppKey;
//    @Value("${trello.app.token}")
//    private String trelloToken;
//    @Value("${trello.app.username}")
//    private String trelloUsername;

    private URI boardsUrl() {
        return UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/members/" + trelloConfig.getTrelloUsername() + "/boards")
                .queryParam("fields", "name,id")
                .queryParam("lists", "all")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .build().encode().toUri();
    }

//    private URI listsUrl() {
//        //return trelloApiEndpoint + "/boards/5ff5b65cbaebaf1bb0b94f8e/lists?key=" + trelloAppKey + "&token=" + trelloToken;
//        return UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/boards/5ff5b65cbaebaf1bb0b94f8e/lists")
//                .queryParam("key", trelloConfig.getTrelloAppKey())
//                .queryParam("token", trelloConfig.getTrelloToken())
//                .build().encode().toUri();
//
//    }

    private URI cardUrl(String name, String desc, String pos, String idList) {
        return UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/cards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("name", name)
                .queryParam("desc", desc)
                .queryParam("pos", pos)
                .queryParam("idList", idList)
                .build()
                .encode()
                .toUri();
    }

    public List<TrelloBoardDto> getTrelloBoards() {

        //TrelloBoardDto[] boardsResponse = restTemplate.getForObject(boardsUrl(), TrelloBoardDto[].class);

        try {
            TrelloBoardDto[] boardsResponse = restTemplate.getForObject(boardsUrl(), TrelloBoardDto[].class);
            return Optional.ofNullable(boardsResponse)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList())
                    .stream()
                    .filter(p -> Objects.nonNull(p.getId()) && Objects.nonNull(p.getName())) //.filter(p -> p.getName().contains("Kodilla"))
                    .collect(Collectors.toList());
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }

    }

//    public List<TrelloListDto> getTrelloLists() {
//        TrelloListDto[] listsResponse = restTemplate.getForObject(listsUrl(), TrelloListDto[].class);
//        return Optional.ofNullable(listsResponse)
//                .map(Arrays::asList)
//                .orElse(Collections.emptyList());
//    }

    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto) {


        return restTemplate.postForObject(cardUrl(trelloCardDto.getName(),
                trelloCardDto.getDescription(), trelloCardDto.getPos(),
        trelloCardDto.getListId()), null, CreatedTrelloCard.class);
    }

}
