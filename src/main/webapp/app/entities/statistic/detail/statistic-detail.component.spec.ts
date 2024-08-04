import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { StatisticDetailComponent } from './statistic-detail.component';

describe('Statistic Management Detail Component', () => {
  let comp: StatisticDetailComponent;
  let fixture: ComponentFixture<StatisticDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StatisticDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: StatisticDetailComponent,
              resolve: { statistic: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(StatisticDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StatisticDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load statistic on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', StatisticDetailComponent);

      // THEN
      expect(instance.statistic()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
