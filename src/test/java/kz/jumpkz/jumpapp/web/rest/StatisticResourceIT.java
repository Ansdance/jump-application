package kz.jumpkz.jumpapp.web.rest;

import static kz.jumpkz.jumpapp.domain.StatisticAsserts.*;
import static kz.jumpkz.jumpapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import kz.jumpkz.jumpapp.IntegrationTest;
import kz.jumpkz.jumpapp.domain.Statistic;
import kz.jumpkz.jumpapp.repository.StatisticRepository;
import kz.jumpkz.jumpapp.service.StatisticService;
import kz.jumpkz.jumpapp.service.dto.StatisticDTO;
import kz.jumpkz.jumpapp.service.mapper.StatisticMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StatisticResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class StatisticResourceIT {

    private static final String DEFAULT_STATISTIC_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_STATISTIC_TITLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/statistics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private StatisticRepository statisticRepository;

    @Mock
    private StatisticRepository statisticRepositoryMock;

    @Autowired
    private StatisticMapper statisticMapper;

    @Mock
    private StatisticService statisticServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatisticMockMvc;

    private Statistic statistic;

    private Statistic insertedStatistic;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Statistic createEntity(EntityManager em) {
        Statistic statistic = new Statistic().statisticTitle(DEFAULT_STATISTIC_TITLE);
        return statistic;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Statistic createUpdatedEntity(EntityManager em) {
        Statistic statistic = new Statistic().statisticTitle(UPDATED_STATISTIC_TITLE);
        return statistic;
    }

    @BeforeEach
    public void initTest() {
        statistic = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedStatistic != null) {
            statisticRepository.delete(insertedStatistic);
            insertedStatistic = null;
        }
    }

    @Test
    @Transactional
    void createStatistic() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Statistic
        StatisticDTO statisticDTO = statisticMapper.toDto(statistic);
        var returnedStatisticDTO = om.readValue(
            restStatisticMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(statisticDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            StatisticDTO.class
        );

        // Validate the Statistic in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedStatistic = statisticMapper.toEntity(returnedStatisticDTO);
        assertStatisticUpdatableFieldsEquals(returnedStatistic, getPersistedStatistic(returnedStatistic));

        insertedStatistic = returnedStatistic;
    }

    @Test
    @Transactional
    void createStatisticWithExistingId() throws Exception {
        // Create the Statistic with an existing ID
        statistic.setId(1L);
        StatisticDTO statisticDTO = statisticMapper.toDto(statistic);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatisticMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(statisticDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Statistic in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStatistics() throws Exception {
        // Initialize the database
        insertedStatistic = statisticRepository.saveAndFlush(statistic);

        // Get all the statisticList
        restStatisticMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statistic.getId().intValue())))
            .andExpect(jsonPath("$.[*].statisticTitle").value(hasItem(DEFAULT_STATISTIC_TITLE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStatisticsWithEagerRelationshipsIsEnabled() throws Exception {
        when(statisticServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStatisticMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(statisticServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllStatisticsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(statisticServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restStatisticMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(statisticRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getStatistic() throws Exception {
        // Initialize the database
        insertedStatistic = statisticRepository.saveAndFlush(statistic);

        // Get the statistic
        restStatisticMockMvc
            .perform(get(ENTITY_API_URL_ID, statistic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(statistic.getId().intValue()))
            .andExpect(jsonPath("$.statisticTitle").value(DEFAULT_STATISTIC_TITLE));
    }

    @Test
    @Transactional
    void getNonExistingStatistic() throws Exception {
        // Get the statistic
        restStatisticMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStatistic() throws Exception {
        // Initialize the database
        insertedStatistic = statisticRepository.saveAndFlush(statistic);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the statistic
        Statistic updatedStatistic = statisticRepository.findById(statistic.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedStatistic are not directly saved in db
        em.detach(updatedStatistic);
        updatedStatistic.statisticTitle(UPDATED_STATISTIC_TITLE);
        StatisticDTO statisticDTO = statisticMapper.toDto(updatedStatistic);

        restStatisticMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statisticDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(statisticDTO))
            )
            .andExpect(status().isOk());

        // Validate the Statistic in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedStatisticToMatchAllProperties(updatedStatistic);
    }

    @Test
    @Transactional
    void putNonExistingStatistic() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statistic.setId(longCount.incrementAndGet());

        // Create the Statistic
        StatisticDTO statisticDTO = statisticMapper.toDto(statistic);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatisticMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statisticDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(statisticDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Statistic in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStatistic() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statistic.setId(longCount.incrementAndGet());

        // Create the Statistic
        StatisticDTO statisticDTO = statisticMapper.toDto(statistic);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(statisticDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Statistic in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStatistic() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statistic.setId(longCount.incrementAndGet());

        // Create the Statistic
        StatisticDTO statisticDTO = statisticMapper.toDto(statistic);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(statisticDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Statistic in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStatisticWithPatch() throws Exception {
        // Initialize the database
        insertedStatistic = statisticRepository.saveAndFlush(statistic);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the statistic using partial update
        Statistic partialUpdatedStatistic = new Statistic();
        partialUpdatedStatistic.setId(statistic.getId());

        restStatisticMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatistic.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStatistic))
            )
            .andExpect(status().isOk());

        // Validate the Statistic in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStatisticUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedStatistic, statistic),
            getPersistedStatistic(statistic)
        );
    }

    @Test
    @Transactional
    void fullUpdateStatisticWithPatch() throws Exception {
        // Initialize the database
        insertedStatistic = statisticRepository.saveAndFlush(statistic);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the statistic using partial update
        Statistic partialUpdatedStatistic = new Statistic();
        partialUpdatedStatistic.setId(statistic.getId());

        partialUpdatedStatistic.statisticTitle(UPDATED_STATISTIC_TITLE);

        restStatisticMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatistic.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedStatistic))
            )
            .andExpect(status().isOk());

        // Validate the Statistic in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertStatisticUpdatableFieldsEquals(partialUpdatedStatistic, getPersistedStatistic(partialUpdatedStatistic));
    }

    @Test
    @Transactional
    void patchNonExistingStatistic() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statistic.setId(longCount.incrementAndGet());

        // Create the Statistic
        StatisticDTO statisticDTO = statisticMapper.toDto(statistic);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatisticMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, statisticDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(statisticDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Statistic in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStatistic() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statistic.setId(longCount.incrementAndGet());

        // Create the Statistic
        StatisticDTO statisticDTO = statisticMapper.toDto(statistic);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(statisticDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Statistic in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStatistic() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        statistic.setId(longCount.incrementAndGet());

        // Create the Statistic
        StatisticDTO statisticDTO = statisticMapper.toDto(statistic);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(statisticDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Statistic in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStatistic() throws Exception {
        // Initialize the database
        insertedStatistic = statisticRepository.saveAndFlush(statistic);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the statistic
        restStatisticMockMvc
            .perform(delete(ENTITY_API_URL_ID, statistic.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return statisticRepository.count();
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

    protected Statistic getPersistedStatistic(Statistic statistic) {
        return statisticRepository.findById(statistic.getId()).orElseThrow();
    }

    protected void assertPersistedStatisticToMatchAllProperties(Statistic expectedStatistic) {
        assertStatisticAllPropertiesEquals(expectedStatistic, getPersistedStatistic(expectedStatistic));
    }

    protected void assertPersistedStatisticToMatchUpdatableProperties(Statistic expectedStatistic) {
        assertStatisticAllUpdatablePropertiesEquals(expectedStatistic, getPersistedStatistic(expectedStatistic));
    }
}
