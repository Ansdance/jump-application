import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IStatisticHistory } from '../statistic-history.model';

@Component({
  standalone: true,
  selector: 'jhi-statistic-history-detail',
  templateUrl: './statistic-history-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class StatisticHistoryDetailComponent {
  statisticHistory = input<IStatisticHistory | null>(null);

  previousState(): void {
    window.history.back();
  }
}
