import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ITask } from 'app/entities/task/task.model';
import { TaskService } from 'app/entities/task/service/task.service';
import { IPlayer } from 'app/entities/player/player.model';
import { PlayerService } from 'app/entities/player/service/player.service';
import { IStatistic } from '../statistic.model';
import { StatisticService } from '../service/statistic.service';
import { StatisticFormService } from './statistic-form.service';

import { StatisticUpdateComponent } from './statistic-update.component';

describe('Statistic Management Update Component', () => {
  let comp: StatisticUpdateComponent;
  let fixture: ComponentFixture<StatisticUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let statisticFormService: StatisticFormService;
  let statisticService: StatisticService;
  let taskService: TaskService;
  let playerService: PlayerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [StatisticUpdateComponent],
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
      .overrideTemplate(StatisticUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StatisticUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    statisticFormService = TestBed.inject(StatisticFormService);
    statisticService = TestBed.inject(StatisticService);
    taskService = TestBed.inject(TaskService);
    playerService = TestBed.inject(PlayerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Task query and add missing value', () => {
      const statistic: IStatistic = { id: 456 };
      const tasks: ITask[] = [{ id: 15024 }];
      statistic.tasks = tasks;

      const taskCollection: ITask[] = [{ id: 942 }];
      jest.spyOn(taskService, 'query').mockReturnValue(of(new HttpResponse({ body: taskCollection })));
      const additionalTasks = [...tasks];
      const expectedCollection: ITask[] = [...additionalTasks, ...taskCollection];
      jest.spyOn(taskService, 'addTaskToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ statistic });
      comp.ngOnInit();

      expect(taskService.query).toHaveBeenCalled();
      expect(taskService.addTaskToCollectionIfMissing).toHaveBeenCalledWith(
        taskCollection,
        ...additionalTasks.map(expect.objectContaining),
      );
      expect(comp.tasksSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Player query and add missing value', () => {
      const statistic: IStatistic = { id: 456 };
      const employee: IPlayer = { id: 16478 };
      statistic.employee = employee;

      const playerCollection: IPlayer[] = [{ id: 18749 }];
      jest.spyOn(playerService, 'query').mockReturnValue(of(new HttpResponse({ body: playerCollection })));
      const additionalPlayers = [employee];
      const expectedCollection: IPlayer[] = [...additionalPlayers, ...playerCollection];
      jest.spyOn(playerService, 'addPlayerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ statistic });
      comp.ngOnInit();

      expect(playerService.query).toHaveBeenCalled();
      expect(playerService.addPlayerToCollectionIfMissing).toHaveBeenCalledWith(
        playerCollection,
        ...additionalPlayers.map(expect.objectContaining),
      );
      expect(comp.playersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const statistic: IStatistic = { id: 456 };
      const task: ITask = { id: 14601 };
      statistic.tasks = [task];
      const employee: IPlayer = { id: 6091 };
      statistic.employee = employee;

      activatedRoute.data = of({ statistic });
      comp.ngOnInit();

      expect(comp.tasksSharedCollection).toContain(task);
      expect(comp.playersSharedCollection).toContain(employee);
      expect(comp.statistic).toEqual(statistic);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStatistic>>();
      const statistic = { id: 123 };
      jest.spyOn(statisticFormService, 'getStatistic').mockReturnValue(statistic);
      jest.spyOn(statisticService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ statistic });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: statistic }));
      saveSubject.complete();

      // THEN
      expect(statisticFormService.getStatistic).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(statisticService.update).toHaveBeenCalledWith(expect.objectContaining(statistic));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStatistic>>();
      const statistic = { id: 123 };
      jest.spyOn(statisticFormService, 'getStatistic').mockReturnValue({ id: null });
      jest.spyOn(statisticService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ statistic: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: statistic }));
      saveSubject.complete();

      // THEN
      expect(statisticFormService.getStatistic).toHaveBeenCalled();
      expect(statisticService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStatistic>>();
      const statistic = { id: 123 };
      jest.spyOn(statisticService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ statistic });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(statisticService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTask', () => {
      it('Should forward to taskService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(taskService, 'compareTask');
        comp.compareTask(entity, entity2);
        expect(taskService.compareTask).toHaveBeenCalledWith(entity, entity2);
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
