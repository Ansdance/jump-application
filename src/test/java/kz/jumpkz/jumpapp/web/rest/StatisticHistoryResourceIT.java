package kz.jumpkz.jumpapp.web.rest;

import static kz.jumpkz.jumpapp.domain.StatisticHistoryAsserts.*;
import static kz.jumpkz.jumpapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import kz.jumpkz.jumpapp.IntegrationTest;
import kz.jumpkz.jumpapp.domain.StatisticHistory;
import kz.jumpkz.jumpapp.domain.enumeration.Language;
import kz.jumpkz.jumpapp.repository.StatisticHistoryRepository;
import kz.jumpkz.jumpapp.service.dto.StatisticHistoryDTO;
import kz.jumpkz.jumpapp.service.mapper.StatisticHistoryMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StatisticHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StatisticHistoryResourceIT {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Language DEFAULT_LANGUAGE = Language.FRENCH;
    private static final Language UPDATED_LANGUAGE = Language.ENGLISH;

    private static final String ENTITY_API_URL = "/api/statistic-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StatisticHistoryRepository statisticHistoryRepository;

    @Autowired
    private StatisticHistoryMapper statisticHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatisticHistoryMockMvc;

    private StatisticHistory statisticHistory;

    private StatisticHistory insertedStatisticHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatisticHistory createEntity(EntityManager em) {
        StatisticHistory statisticHistory = new StatisticHistory()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .language(DEFAULT_LANGUAGE);
        return statisticHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatisticHistory createUpdatedEntity(EntityManager em) {
        StatisticHistory statisticHistory = new StatisticHistory()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .language(UPDATED_LANGUAGE);
        return statisticHistory;
    }

    @BeforeEach
    public void initTest() {
        statisticHistory = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedStatisticHistory != null) {
            statisticHistoryRepository.delete(insertedStatisticHistory);
            insertedStatisticHistory = null;
        }
    }

    @Test
    @Transactional
    void createStatisticHistory() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the StatisticHistory
        StatisticHistoryDTO statisticHistoryDTO = statisticHistoryMapper.toDto(statisticHistory);
        var returnedStatisticHistoryDTO = om.readValue(
            restStatisticHistoryMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(statisticHistoryDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            StatisticHistoryDTO.class
        );

        // Validate the StatisticHistory in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedStatisticHistory = statisticHistoryMapper.toEntity(returnedStatisticHistoryDTO);
        assertStatisticHistoryUpdatableFieldsEquals(returnedStatisticHistory, getPersistedStatisticHistory(returnedStatisticHistory));

        insertedStatisticHistory = returnedStatisticHistory;
    }

    @Test
    @Transactional
    void createStatisticHistoryWithExistingId() throws Exception {
        // Create the StatisticHistory with an existing ID
        statisticHistory.setId(1L);
        StatisticHistoryDTO statisticHistoryDTO = statisticHistoryMapper.toDto(statisticHistory);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatisticHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(statisticHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatisticHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStatisticHistories() throws Exception {
        // Initialize the database
        insertedStatisticHistory = statisticHistoryRepository.saveAndFlush(statisticHistory);

        // Get all the statisticHistoryList
        restStatisticHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statisticHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    void getStatisticHistory() throws Exception {
        // Initialize the database
        insertedStatisticHistory = statisticHistoryRepository.saveAndFlush(statisticHistory);

        // Get the statisticHistory
        restStatisticHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, statisticHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(statisticHistory.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingStatisticHistory() throws Exception {
        // Get the statisticHistory
        restStatisticHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStatisticHistory() throws Exception {
        // Initialize the database
        insertedStatisticHistory = statisticHistoryRepository.saveAndFlush(statisticHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the statisticHistory
        StatisticHistory updatedStatisticHistory = statisticHistoryRepository.findById(statisticHistory.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStatisticHistory are not directly saved in db
        em.detach(updatedStatisticHistory);
        updatedStatisticHistory.startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE).language(UPDATED_LANGUAGE);
        StatisticHistoryDTO statisticHistoryDTO = statisticHistoryMapper.toDto(updatedStatisticHistory);

        restStatisticHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statisticHistoryDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(statisticHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the StatisticHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStatisticHistoryToMatchAllProperties(updatedStatisticHistory);
    }

    @Test
    @Transactional
    void putNonExistingStatisticHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statisticHistory.setId(longCount.incrementAndGet());

        // Create the StatisticHistory
        StatisticHistoryDTO statisticHistoryDTO = statisticHistoryMapper.toDto(statisticHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatisticHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statisticHistoryDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(statisticHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatisticHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStatisticHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statisticHistory.setId(longCount.incrementAndGet());

        // Create the StatisticHistory
        StatisticHistoryDTO statisticHistoryDTO = statisticHistoryMapper.toDto(statisticHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(statisticHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatisticHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStatisticHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statisticHistory.setId(longCount.incrementAndGet());

        // Create the StatisticHistory
        StatisticHistoryDTO statisticHistoryDTO = statisticHistoryMapper.toDto(statisticHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticHistoryMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(statisticHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StatisticHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStatisticHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedStatisticHistory = statisticHistoryRepository.saveAndFlush(statisticHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the statisticHistory using partial update
        StatisticHistory partialUpdatedStatisticHistory = new StatisticHistory();
        partialUpdatedStatisticHistory.setId(statisticHistory.getId());

        partialUpdatedStatisticHistory.startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE).language(UPDATED_LANGUAGE);

        restStatisticHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatisticHistory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStatisticHistory))
            )
            .andExpect(status().isOk());

        // Validate the StatisticHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStatisticHistoryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedStatisticHistory, statisticHistory),
            getPersistedStatisticHistory(statisticHistory)
        );
    }

    @Test
    @Transactional
    void fullUpdateStatisticHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedStatisticHistory = statisticHistoryRepository.saveAndFlush(statisticHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the statisticHistory using partial update
        StatisticHistory partialUpdatedStatisticHistory = new StatisticHistory();
        partialUpdatedStatisticHistory.setId(statisticHistory.getId());

        partialUpdatedStatisticHistory.startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE).language(UPDATED_LANGUAGE);

        restStatisticHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatisticHistory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStatisticHistory))
            )
            .andExpect(status().isOk());

        // Validate the StatisticHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStatisticHistoryUpdatableFieldsEquals(
            partialUpdatedStatisticHistory,
            getPersistedStatisticHistory(partialUpdatedStatisticHistory)
        );
    }

    @Test
    @Transactional
    void patchNonExistingStatisticHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statisticHistory.setId(longCount.incrementAndGet());

        // Create the StatisticHistory
        StatisticHistoryDTO statisticHistoryDTO = statisticHistoryMapper.toDto(statisticHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatisticHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, statisticHistoryDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(statisticHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatisticHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStatisticHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statisticHistory.setId(longCount.incrementAndGet());

        // Create the StatisticHistory
        StatisticHistoryDTO statisticHistoryDTO = statisticHistoryMapper.toDto(statisticHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(statisticHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatisticHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStatisticHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statisticHistory.setId(longCount.incrementAndGet());

        // Create the StatisticHistory
        StatisticHistoryDTO statisticHistoryDTO = statisticHistoryMapper.toDto(statisticHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(statisticHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StatisticHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStatisticHistory() throws Exception {
        // Initialize the database
        insertedStatisticHistory = statisticHistoryRepository.saveAndFlush(statisticHistory);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the statisticHistory
        restStatisticHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, statisticHistory.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return statisticHistoryRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected StatisticHistory getPersistedStatisticHistory(StatisticHistory statisticHistory) {
        return statisticHistoryRepository.findById(statisticHistory.getId()).orElseThrow();
    }

    protected void assertPersistedStatisticHistoryToMatchAllProperties(StatisticHistory expectedStatisticHistory) {
        assertStatisticHistoryAllPropertiesEquals(expectedStatisticHistory, getPersistedStatisticHistory(expectedStatisticHistory));
    }

    protected void assertPersistedStatisticHistoryToMatchUpdatableProperties(StatisticHistory expectedStatisticHistory) {
        assertStatisticHistoryAllUpdatablePropertiesEquals(
            expectedStatisticHistory,
            getPersistedStatisticHistory(expectedStatisticHistory)
        );
    }
}
