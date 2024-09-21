package com.education.vndictionary.controllers;

import com.education.vndictionary.common.Messages;
import com.education.vndictionary.common.HttpResponseUtil;
import com.education.vndictionary.dtos.BaseHttpResponse;
import com.education.vndictionary.dtos.PaginatedHttpResponse;
import com.education.vndictionary.dtos.TopicDto;
import com.education.vndictionary.exceptions.AppErrorException;
import com.education.vndictionary.services.TopicService;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class TopicsController {
    private final TopicService topicService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("view/topics")
    public PaginatedHttpResponse viewTopics(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
    ) {
        try {
            logger.info("View topics");
            return this.topicService.viewAllTopicWithPaging(page, size);
        } catch (Exception e) {
            logger.error("Error: ", e);
            throw new AppErrorException(HttpStatus.INTERNAL_SERVER_ERROR.value(), Messages.INTERNAL_SERVER_ERROR);
        } finally {
            logger.info("Finished view topics");
        }
    }

    @GetMapping("view/topics/popular")
    public BaseHttpResponse viewPopularTopics(@RequestParam(value = "size", required = false) Integer size) {
        try {
            logger.info("View popular topics");
            List<TopicDto> topics = this.topicService.viewPopularTopics(size);
            return HttpResponseUtil.createSuccessResponse(topics);

        } catch (Exception e) {
            logger.error("Error: ", e);
            throw new AppErrorException(HttpStatus.INTERNAL_SERVER_ERROR.value(), Messages.INTERNAL_SERVER_ERROR);
        } finally {
            logger.info("Finished view popular topics");
        }

    }

    @GetMapping("view/topics/{id}")
    public BaseHttpResponse getTopicById(@PathVariable("id") Integer id) {
        try {
            logger.info(String.format("Get topic by id: %d", id));
            TopicDto dto = this.topicService.getTopicById(id);
            return HttpResponseUtil.createSuccessResponse(dto);

        } catch (Exception e) {
            logger.error("Error: ", e);
            throw new AppErrorException(HttpStatus.INTERNAL_SERVER_ERROR.value(), Messages.INTERNAL_SERVER_ERROR);
        } finally {
            logger.info(String.format("Finished get topic by id: %d", id));
        }
    }

    @GetMapping("view/topics/search")
    public BaseHttpResponse searchTopic(
            @RequestParam(value = "q") String q,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "page", required = false) Integer page
    ) {
        try {
            logger.info(String.format("Search topic by keyword: %s", q));
            List<TopicDto> topics = this.topicService.searchTopics(q, size, page);
            return HttpResponseUtil.createSuccessResponse(topics);

        } catch (Exception e) {
            logger.error("Error: ", e);
            throw new AppErrorException(HttpStatus.INTERNAL_SERVER_ERROR.value(), Messages.INTERNAL_SERVER_ERROR);
        } finally {
            logger.info(String.format("Finished search topic by keyword: %s", q));
        }
    }

    @PostMapping(value = "topics")
    public BaseHttpResponse createTopic(@RequestBody TopicDto topicDto) {
        try {
            logger.info("Create topic");
            this.topicService.createTopic(topicDto);
            return HttpResponseUtil.createSuccessResponse(Messages.SUCCESS_UPSERT_OPERATION);
        } catch (Exception e) {
            logger.error("Error: ", e);
            throw new AppErrorException(HttpStatus.INTERNAL_SERVER_ERROR.value(), Messages.INTERNAL_SERVER_ERROR);
        } finally {
            logger.info("Finished create topic");
        }
    }

    @PutMapping("topics/{id}")
    public BaseHttpResponse updateTopic(@PathVariable("id") Integer id, @RequestBody TopicDto topicDto) {
        try {
            logger.info("Update topic");
            this.topicService.updateTopic(id, topicDto);
            return HttpResponseUtil.createSuccessResponse(Messages.SUCCESS_UPSERT_OPERATION);
        } catch (Exception e) {
            logger.error("Error: ", e);
            throw new AppErrorException(HttpStatus.INTERNAL_SERVER_ERROR.value(), Messages.INTERNAL_SERVER_ERROR);
        } finally {
            logger.info("Finished update topic");
        }
    }

    @DeleteMapping("topics/{id}")
    public BaseHttpResponse deleteTopic(@PathVariable("id") Integer id) {
        try {
            logger.info(String.format("Delete topic by id: %d", id));
            this.topicService.deleteTopic(id);
            return HttpResponseUtil.createSuccessResponse();
        } catch (Exception e) {
            logger.error("Error: ", e);
            throw new AppErrorException(HttpStatus.INTERNAL_SERVER_ERROR.value(), Messages.INTERNAL_SERVER_ERROR);
        } finally {
            logger.info(String.format("Finished delete topic by id: %d", id));
        }
    }

}
