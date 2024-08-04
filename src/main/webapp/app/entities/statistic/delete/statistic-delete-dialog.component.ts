import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IStatistic } from '../statistic.model';
import { StatisticService } from '../service/statistic.service';

@Component({
  standalone: true,
  templateUrl: './statistic-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class StatisticDeleteDialogComponent {
  statistic?: IStatistic;

  protected statisticService = inject(StatisticService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.statisticService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
