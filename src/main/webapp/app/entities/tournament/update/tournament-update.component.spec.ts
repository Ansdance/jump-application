import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ILocation } from 'app/entities/location/location.model';
import { LocationService } from 'app/entities/location/service/location.service';
import { TournamentService } from '../service/tournament.service';
import { ITournament } from '../tournament.model';
import { TournamentFormService } from './tournament-form.service';

import { TournamentUpdateComponent } from './tournament-update.component';

describe('Tournament Management Update Component', () => {
  let comp: TournamentUpdateComponent;
  let fixture: ComponentFixture<TournamentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tournamentFormService: TournamentFormService;
  let tournamentService: TournamentService;
  let locationService: LocationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TournamentUpdateComponent],
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
      .overrideTemplate(TournamentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TournamentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tournamentFormService = TestBed.inject(TournamentFormService);
    tournamentService = TestBed.inject(TournamentService);
    locationService = TestBed.inject(LocationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call location query and add missing value', () => {
      const tournament: ITournament = { id: 456 };
      const location: ILocation = { id: 22651 };
      tournament.location = location;

      const locationCollection: ILocation[] = [{ id: 7158 }];
      jest.spyOn(locationService, 'query').mockReturnValue(of(new HttpResponse({ body: locationCollection })));
      const expectedCollection: ILocation[] = [location, ...locationCollection];
      jest.spyOn(locationService, 'addLocationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tournament });
      comp.ngOnInit();

      expect(locationService.query).toHaveBeenCalled();
      expect(locationService.addLocationToCollectionIfMissing).toHaveBeenCalledWith(locationCollection, location);
      expect(comp.locationsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tournament: ITournament = { id: 456 };
      const location: ILocation = { id: 10137 };
      tournament.location = location;

      activatedRoute.data = of({ tournament });
      comp.ngOnInit();

      expect(comp.locationsCollection).toContain(location);
      expect(comp.tournament).toEqual(tournament);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITournament>>();
      const tournament = { id: 123 };
      jest.spyOn(tournamentFormService, 'getTournament').mockReturnValue(tournament);
      jest.spyOn(tournamentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tournament });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tournament }));
      saveSubject.complete();

      // THEN
      expect(tournamentFormService.getTournament).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tournamentService.update).toHaveBeenCalledWith(expect.objectContaining(tournament));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITournament>>();
      const tournament = { id: 123 };
      jest.spyOn(tournamentFormService, 'getTournament').mockReturnValue({ id: null });
      jest.spyOn(tournamentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tournament: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tournament }));
      saveSubject.complete();

      // THEN
      expect(tournamentFormService.getTournament).toHaveBeenCalled();
      expect(tournamentService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITournament>>();
      const tournament = { id: 123 };
      jest.spyOn(tournamentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tournament });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tournamentService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareLocation', () => {
      it('Should forward to locationService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(locationService, 'compareLocation');
        comp.compareLocation(entity, entity2);
        expect(locationService.compareLocation).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
