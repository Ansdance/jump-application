package kz.jumpkz.jumpapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import kz.jumpkz.jumpapp.repository.StatisticHistoryRepository;
import kz.jumpkz.jumpapp.service.StatisticHistoryService;
import kz.jumpkz.jumpapp.service.dto.StatisticHistoryDTO;
import kz.jumpkz.jumpapp.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link kz.jumpkz.jumpapp.domain.StatisticHistory}.
 */
@RestController
@RequestMapping("/api/statistic-histories")
public class StatisticHistoryResource {

    private static final Logger log = LoggerFactory.getLogger(StatisticHistoryResource.class);

    private static final String ENTITY_NAME = "statisticHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatisticHistoryService statisticHistoryService;

    private final StatisticHistoryRepository statisticHistoryRepository;

    public StatisticHistoryResource(
        StatisticHistoryService statisticHistoryService,
        StatisticHistoryRepository statisticHistoryRepository
    ) {
        this.statisticHistoryService = statisticHistoryService;
        this.statisticHistoryRepository = statisticHistoryRepository;
    }

    /**
     * {@code POST  /statistic-histories} : Create a new statisticHistory.
     *
     * @param statisticHistoryDTO the statisticHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statisticHistoryDTO, or with status {@code 400 (Bad Request)} if the statisticHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<StatisticHistoryDTO> createStatisticHistory(@RequestBody StatisticHistoryDTO statisticHistoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save StatisticHistory : {}", statisticHistoryDTO);
        if (statisticHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new statisticHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        statisticHistoryDTO = statisticHistoryService.save(statisticHistoryDTO);
        return ResponseEntity.created(new URI("/api/statistic-histories/" + statisticHistoryDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, statisticHistoryDTO.getId().toString()))
            .body(statisticHistoryDTO);
    }

    /**
     * {@code PUT  /statistic-histories/:id} : Updates an existing statisticHistory.
     *
     * @param id the id of the statisticHistoryDTO to save.
     * @param statisticHistoryDTO the statisticHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statisticHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the statisticHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statisticHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StatisticHistoryDTO> updateStatisticHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StatisticHistoryDTO statisticHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StatisticHistory : {}, {}", id, statisticHistoryDTO);
        if (statisticHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statisticHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statisticHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        statisticHistoryDTO = statisticHistoryService.update(statisticHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statisticHistoryDTO.getId().toString()))
            .body(statisticHistoryDTO);
    }

    /**
     * {@code PATCH  /statistic-histories/:id} : Partial updates given fields of an existing statisticHistory, field will ignore if it is null
     *
     * @param id the id of the statisticHistoryDTO to save.
     * @param statisticHistoryDTO the statisticHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statisticHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the statisticHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the statisticHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the statisticHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StatisticHistoryDTO> partialUpdateStatisticHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StatisticHistoryDTO statisticHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StatisticHistory partially : {}, {}", id, statisticHistoryDTO);
        if (statisticHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statisticHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statisticHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StatisticHistoryDTO> result = statisticHistoryService.partialUpdate(statisticHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statisticHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /statistic-histories} : get all the statisticHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statisticHistories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<StatisticHistoryDTO>> getAllStatisticHistories(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of StatisticHistories");
        Page<StatisticHistoryDTO> page = statisticHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /statistic-histories/:id} : get the "id" statisticHistory.
     *
     * @param id the id of the statisticHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statisticHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StatisticHistoryDTO> getStatisticHistory(@PathVariable("id") Long id) {
        log.debug("REST request to get StatisticHistory : {}", id);
        Optional<StatisticHistoryDTO> statisticHistoryDTO = statisticHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statisticHistoryDTO);
    }

    /**
     * {@code DELETE  /statistic-histories/:id} : delete the "id" statisticHistory.
     *
     * @param id the id of the statisticHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatisticHistory(@PathVariable("id") Long id) {
        log.debug("REST request to delete StatisticHistory : {}", id);
        statisticHistoryService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
