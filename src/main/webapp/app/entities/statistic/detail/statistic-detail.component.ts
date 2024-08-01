import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IStatistic } from '../statistic.model';

@Component({
  standalone: true,
  selector: 'jhi-statistic-detail',
  templateUrl: './statistic-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class StatisticDetailComponent {
  statistic = input<IStatistic | null>(null);

  previousState(): void {
    window.history.back();
  }
}
