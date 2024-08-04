import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IStatistic } from 'app/entities/statistic/statistic.model';
import { StatisticService } from 'app/entities/statistic/service/statistic.service';
import { ITournament } from 'app/entities/tournament/tournament.model';
import { TournamentService } from 'app/entities/tournament/service/tournament.service';
import { IPlayer } from 'app/entities/player/player.model';
import { PlayerService } from 'app/entities/player/service/player.service';
import { IStatisticHistory } from '../statistic-history.model';
import { StatisticHistoryService } from '../service/statistic-history.service';
import { StatisticHistoryFormService } from './statistic-history-form.service';

import { StatisticHistoryUpdateComponent } from './statistic-history-update.component';

describe('StatisticHistory Management Update Component', () => {
  let comp: StatisticHistoryUpdateComponent;
  let fixture: ComponentFixture<StatisticHistoryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let statisticHistoryFormService: StatisticHistoryFormService;
  let statisticHistoryService: StatisticHistoryService;
  let statisticService: StatisticService;
  let tournamentService: TournamentService;
  let playerService: PlayerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [StatisticHistoryUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(StatisticHistoryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StatisticHistoryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    statisticHistoryFormService = TestBed.inject(StatisticHistoryFormService);
    statisticHistoryService = TestBed.inject(StatisticHistoryService);
    statisticService = TestBed.inject(StatisticService);
    tournamentService = TestBed.inject(TournamentService);
    playerService = TestBed.inject(PlayerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call statistic query and add missing value', () => {
      const statisticHistory: IStatisticHistory = { id: 456 };
      const statistic: IStatistic = { id: 15856 };
      statisticHistory.statistic = statistic;

      const statisticCollection: IStatistic[] = [{ id: 15920 }];
      jest.spyOn(statisticService, 'query').mockReturnValue(of(new HttpResponse({ body: statisticCollection })));
      const expectedCollection: IStatistic[] = [statistic, ...statisticCollection];
      jest.spyOn(statisticService, 'addStatisticToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ statisticHistory });
      comp.ngOnInit();

      expect(statisticService.query).toHaveBeenCalled();
      expect(statisticService.addStatisticToCollectionIfMissing).toHaveBeenCalledWith(statisticCollection, statistic);
      expect(comp.statisticsCollection).toEqual(expectedCollection);
    });

    it('Should call department query and add missing value', () => {
      const statisticHistory: IStatisticHistory = { id: 456 };
      const department: ITournament = { id: 23665 };
      statisticHistory.department = department;

      const departmentCollection: ITournament[] = [{ id: 10391 }];
      jest.spyOn(tournamentService, 'query').mockReturnValue(of(new HttpResponse({ body: departmentCollection })));
      const expectedCollection: ITournament[] = [department, ...departmentCollection];
      jest.spyOn(tournamentService, 'addTournamentToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ statisticHistory });
      comp.ngOnInit();

      expect(tournamentService.query).toHaveBeenCalled();
      expect(tournamentService.addTournamentToCollectionIfMissing).toHaveBeenCalledWith(departmentCollection, department);
      expect(comp.departmentsCollection).toEqual(expectedCollection);
    });

    it('Should call employee query and add missing value', () => {
      const statisticHistory: IStatisticHistory = { id: 456 };
      const employee: IPlayer = { id: 3487 };
      statisticHistory.employee = employee;

      const employeeCollection: IPlayer[] = [{ id: 25495 }];
      jest.spyOn(playerService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
      const expectedCollection: IPlayer[] = [employee, ...employeeCollection];
      jest.spyOn(playerService, 'addPlayerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ statisticHistory });
      comp.ngOnInit();

      expect(playerService.query).toHaveBeenCalled();
      expect(playerService.addPlayerToCollectionIfMissing).toHaveBeenCalledWith(employeeCollection, employee);
      expect(comp.employeesCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const statisticHistory: IStatisticHistory = { id: 456 };
      const statistic: IStatistic = { id: 9554 };
      statisticHistory.statistic = statistic;
      const department: ITournament = { id: 199 };
      statisticHistory.department = department;
      const employee: IPlayer = { id: 29283 };
      statisticHistory.employee = employee;

      activatedRoute.data = of({ statisticHistory });
      comp.ngOnInit();

      expect(comp.statisticsCollection).toContain(statistic);
      expect(comp.departmentsCollection).toContain(department);
      expect(comp.employeesCollection).toContain(employee);
      expect(comp.statisticHistory).toEqual(statisticHistory);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStatisticHistory>>();
      const statisticHistory = { id: 123 };
      jest.spyOn(statisticHistoryFormService, 'getStatisticHistory').mockReturnValue(statisticHistory);
      jest.spyOn(statisticHistoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ statisticHistory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: statisticHistory }));
      saveSubject.complete();

      // THEN
      expect(statisticHistoryFormService.getStatisticHistory).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(statisticHistoryService.update).toHaveBeenCalledWith(expect.objectContaining(statisticHistory));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStatisticHistory>>();
      const statisticHistory = { id: 123 };
      jest.spyOn(statisticHistoryFormService, 'getStatisticHistory').mockReturnValue({ id: null });
      jest.spyOn(statisticHistoryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ statisticHistory: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: statisticHistory }));
      saveSubject.complete();

      // THEN
      expect(statisticHistoryFormService.getStatisticHistory).toHaveBeenCalled();
      expect(statisticHistoryService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStatisticHistory>>();
      const statisticHistory = { id: 123 };
      jest.spyOn(statisticHistoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ statisticHistory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(statisticHistoryService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareStatistic', () => {
      it('Should forward to statisticService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(statisticService, 'compareStatistic');
        comp.compareStatistic(entity, entity2);
        expect(statisticService.compareStatistic).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTournament', () => {
      it('Should forward to tournamentService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(tournamentService, 'compareTournament');
        comp.compareTournament(entity, entity2);
        expect(tournamentService.compareTournament).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePlayer', () => {
      it('Should forward to playerService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(playerService, 'comparePlayer');
        comp.comparePlayer(entity, entity2);
        expect(playerService.comparePlayer).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
