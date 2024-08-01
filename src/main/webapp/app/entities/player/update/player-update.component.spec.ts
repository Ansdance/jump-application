import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ITournament } from 'app/entities/tournament/tournament.model';
import { TournamentService } from 'app/entities/tournament/service/tournament.service';
import { PlayerService } from '../service/player.service';
import { IPlayer } from '../player.model';
import { PlayerFormService } from './player-form.service';

import { PlayerUpdateComponent } from './player-update.component';

describe('Player Management Update Component', () => {
  let comp: PlayerUpdateComponent;
  let fixture: ComponentFixture<PlayerUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let playerFormService: PlayerFormService;
  let playerService: PlayerService;
  let tournamentService: TournamentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PlayerUpdateComponent],
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
      .overrideTemplate(PlayerUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlayerUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    playerFormService = TestBed.inject(PlayerFormService);
    playerService = TestBed.inject(PlayerService);
    tournamentService = TestBed.inject(TournamentService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Player query and add missing value', () => {
      const player: IPlayer = { id: 456 };
      const manager: IPlayer = { id: 12284 };
      player.manager = manager;

      const playerCollection: IPlayer[] = [{ id: 27138 }];
      jest.spyOn(playerService, 'query').mockReturnValue(of(new HttpResponse({ body: playerCollection })));
      const additionalPlayers = [manager];
      const expectedCollection: IPlayer[] = [...additionalPlayers, ...playerCollection];
      jest.spyOn(playerService, 'addPlayerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ player });
      comp.ngOnInit();

      expect(playerService.query).toHaveBeenCalled();
      expect(playerService.addPlayerToCollectionIfMissing).toHaveBeenCalledWith(
        playerCollection,
        ...additionalPlayers.map(expect.objectContaining),
      );
      expect(comp.playersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Tournament query and add missing value', () => {
      const player: IPlayer = { id: 456 };
      const tournament: ITournament = { id: 27401 };
      player.tournament = tournament;

      const tournamentCollection: ITournament[] = [{ id: 12007 }];
      jest.spyOn(tournamentService, 'query').mockReturnValue(of(new HttpResponse({ body: tournamentCollection })));
      const additionalTournaments = [tournament];
      const expectedCollection: ITournament[] = [...additionalTournaments, ...tournamentCollection];
      jest.spyOn(tournamentService, 'addTournamentToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ player });
      comp.ngOnInit();

      expect(tournamentService.query).toHaveBeenCalled();
      expect(tournamentService.addTournamentToCollectionIfMissing).toHaveBeenCalledWith(
        tournamentCollection,
        ...additionalTournaments.map(expect.objectContaining),
      );
      expect(comp.tournamentsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const player: IPlayer = { id: 456 };
      const manager: IPlayer = { id: 14394 };
      player.manager = manager;
      const tournament: ITournament = { id: 22961 };
      player.tournament = tournament;

      activatedRoute.data = of({ player });
      comp.ngOnInit();

      expect(comp.playersSharedCollection).toContain(manager);
      expect(comp.tournamentsSharedCollection).toContain(tournament);
      expect(comp.player).toEqual(player);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlayer>>();
      const player = { id: 123 };
      jest.spyOn(playerFormService, 'getPlayer').mockReturnValue(player);
      jest.spyOn(playerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ player });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: player }));
      saveSubject.complete();

      // THEN
      expect(playerFormService.getPlayer).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(playerService.update).toHaveBeenCalledWith(expect.objectContaining(player));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlayer>>();
      const player = { id: 123 };
      jest.spyOn(playerFormService, 'getPlayer').mockReturnValue({ id: null });
      jest.spyOn(playerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ player: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: player }));
      saveSubject.complete();

      // THEN
      expect(playerFormService.getPlayer).toHaveBeenCalled();
      expect(playerService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlayer>>();
      const player = { id: 123 };
      jest.spyOn(playerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ player });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(playerService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePlayer', () => {
      it('Should forward to playerService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(playerService, 'comparePlayer');
        comp.comparePlayer(entity, entity2);
        expect(playerService.comparePlayer).toHaveBeenCalledWith(entity, entity2);
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
  });
});
